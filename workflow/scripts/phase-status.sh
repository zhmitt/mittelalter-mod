#!/usr/bin/env bash

set -euo pipefail

json_output=false
requested_change=""

while [[ $# -gt 0 ]]; do
  case "$1" in
    --json)
      json_output=true
      shift
      ;;
    --change)
      requested_change="${2:-}"
      if [[ -z "$requested_change" ]]; then
        echo "Missing value for --change" >&2
        exit 1
      fi
      shift 2
      ;;
    *)
      echo "Unknown argument: $1" >&2
      exit 1
      ;;
  esac
done

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$repo_root"

changes_root="openspec/changes"

count_matches() {
  local pattern="$1"
  if command -v rg >/dev/null 2>&1; then
    { rg -N "$pattern" "$2" || true; } | wc -l | tr -d ' '
  else
    grep -Ec "$pattern" "$2" || true
  fi
}

latest_orchestration_state() {
  local change="$1"
  [[ -f workflow/state/status.md ]] || return 0
  awk -v target="$change" '
    function finish() {
      if (explicit != "") state = explicit
      else if (legacy != "") state = legacy
      explicit = legacy = ""
    }
    /^## / || /^- Change: / { finish(); in_change = 0 }
    $0 == "- Change: " target { in_change = 1; next }
    in_change && /^- Orchestration: / { explicit = substr($0, 18) }
    in_change && /^- (Status|State): / {
      value = $0; sub(/^- (Status|State): /, "", value)
      if (value ~ /^(active_write|external_wait|user_action_pending|parked|ready_for_verify|ready_for_archive|done)$/)
        legacy = value
      # Implemented is not Done, but supersedes a historical writing claim.
      else if (value == "implemented" || value == "archived") legacy = "derive"
    }
    END { finish(); print state }
  ' workflow/state/status.md
}

active_changes=()
while IFS= read -r line; do
  active_changes+=("$line")
done < <(find "$changes_root" -mindepth 1 -maxdepth 1 -type d ! -name archive | sort)

if [[ ${#active_changes[@]} -eq 0 ]]; then
  if [[ -n "$requested_change" ]]; then
    echo "Active change not found: $requested_change" >&2
    exit 1
  fi
  if [[ "$json_output" == true ]]; then
    cat <<EOF
{
  "change": "",
  "state": "no_change",
  "next_step": "Create a new change in openspec/changes/",
  "tasks_total": 0,
  "tasks_complete": 0
}
EOF
  else
    echo "Change: none"
    echo "State: no_change"
    echo "Tasks: 0/0"
    echo "Next: Create a new change in openspec/changes/"
  fi
  exit 0
fi

change_dir=""
if [[ -n "$requested_change" ]]; then
  change_dir="$changes_root/$requested_change"
  if [[ ! -d "$change_dir" ]]; then
    echo "Active change not found: $requested_change" >&2
    exit 1
  fi
elif [[ ${#active_changes[@]} -gt 1 ]]; then
  # A read-only overview does not need a writing-owner selection.
  [[ "$json_output" != true ]] || printf '[\n'
  separator=""
  for change_dir in "${active_changes[@]}"; do
    if [[ "$json_output" == true ]]; then
      printf '%s' "$separator"
      "$0" --change "$(basename "$change_dir")" --json
      separator=$',\n'
    else
      "$0" --change "$(basename "$change_dir")"
      echo
    fi
  done
  [[ "$json_output" != true ]] || printf ']\n'
  exit 0
else
  change_dir="${active_changes[0]}"
fi

change_id="$(basename "$change_dir")"
proposal_exists=false
design_exists=false
tasks_exists=false
verification_exists=false
report_exists=false
status_entry_exists=false
spec_count=0
tasks_total=0
tasks_complete=0
orchestration_state=""

[[ -f "$change_dir/proposal.md" ]] && proposal_exists=true
[[ -f "$change_dir/design.md" ]] && design_exists=true
[[ -f "$change_dir/tasks.md" ]] && tasks_exists=true
[[ -f "$change_dir/verification.md" ]] && verification_exists=true

if [[ -d "$change_dir/specs" ]]; then
  spec_count="$(find "$change_dir/specs" -type f -name 'spec.md' | wc -l | tr -d ' ')"
fi

if find workflow/state/reports -type f -name "*${change_id}*.md" | grep -q .; then
  report_exists=true
fi

if [[ -f workflow/state/status.md ]] && grep -Fq "Change: ${change_id}" workflow/state/status.md; then
  status_entry_exists=true
fi

if [[ "$tasks_exists" == true ]]; then
  tasks_total="$(count_matches '^- \[( |x|X)\]' "$change_dir/tasks.md")"
  tasks_complete="$(count_matches '^- \[[xX]\]' "$change_dir/tasks.md")"
fi

orchestration_state="$(latest_orchestration_state "$change_id")"

state="draft"
next_step="Complete proposal and delta specs"
scaffold_state="complete"
if [[ "$proposal_exists" != true || "$spec_count" -eq 0 ]]; then
  scaffold_state="draft"
elif [[ "$design_exists" != true ]]; then
  scaffold_state="ready_for_design"
elif [[ "$tasks_exists" != true ]]; then
  scaffold_state="ready_for_tasks"
fi

# Scheduling is independent of document completeness or checked task count.
case "$orchestration_state" in
  active_write) state="active_write"; next_step="Continue the current delivery slice" ;;
  external_wait) state="external_wait"; next_step="Resume only when the recorded external trigger occurs" ;;
  user_action_pending) state="user_action_pending"; next_step="Resume only when the recorded user action occurs" ;;
  parked) state="parked"; next_step="Resume only after explicit activation" ;;
  ready_for_verify) state="ready_for_verify"; next_step="Verify the preserved implementation" ;;
  ready_for_archive|done|derive|"") ;;
  *) echo "Invalid orchestration state for ${change_id}: ${orchestration_state}" >&2; exit 1 ;;
esac

if [[ "$state" != draft ]]; then
  : # Explicit non-terminal state already resolved above.
elif [[ "$proposal_exists" != true || "$spec_count" -eq 0 ]]; then
  state="draft"
  next_step="Complete proposal and delta specs"
elif [[ "$design_exists" != true ]]; then
  state="ready_for_design"
  next_step="Write design.md"
elif [[ "$tasks_exists" != true ]]; then
  state="ready_for_tasks"
  next_step="Write tasks.md"
elif [[ "$tasks_total" -eq 0 || "$tasks_complete" -lt "$tasks_total" ]]; then
  state="parked"
  next_step="Resume only after explicit activation"
elif [[ "$verification_exists" != true || "$report_exists" != true || "$status_entry_exists" != true ]]; then
  state="ready_for_verify"
  next_step="Add verification.md and workflow evidence"
else
  state="ready_for_archive"
  next_step="Archive the change into openspec/changes/archive/"
fi

if [[ "$json_output" == true ]]; then
  cat <<EOF
{
  "change": "${change_id}",
  "state": "${state}",
  "scaffold_state": "${scaffold_state}",
  "next_step": "${next_step}",
  "tasks_total": ${tasks_total},
  "tasks_complete": ${tasks_complete}
}
EOF
else
  echo "Change: ${change_id}"
  echo "State: ${state}"
  echo "Scaffold: ${scaffold_state}"
  echo "Tasks: ${tasks_complete}/${tasks_total}"
  echo "Next: ${next_step}"
fi
