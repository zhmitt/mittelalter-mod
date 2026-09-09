#!/usr/bin/env bash

# checkpoint-commit-check.sh -- validate an explicit incomplete OpenSpec checkpoint
set -euo pipefail

change_id=""
message_file=".git/COMMIT_EDITMSG"
quiet=false
staged_only=false

while [[ $# -gt 0 ]]; do
  case "$1" in
    --change) change_id="${2:-}"; shift 2 ;;
    --message-file) message_file="${2:-}"; shift 2 ;;
    --quiet) quiet=true; shift ;;
    --staged-only) staged_only=true; shift ;;
    *) echo "Unknown argument: $1" >&2; exit 2 ;;
  esac
done

if [[ ! "$change_id" =~ ^[A-Za-z0-9][A-Za-z0-9._-]*$ ]]; then
  echo "Use --change <active-change-id>." >&2
  exit 2
fi

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$repo_root"

failures=()
staged_files="$(git diff --cached --name-only --diff-filter=ACMR)"

is_staged() {
  grep -Fxq "$1" <<< "$staged_files"
}

indexed() {
  git show ":$1" 2>/dev/null
}

require_staged() {
  local path="$1"
  if ! is_staged "$path"; then
    failures+=("required checkpoint evidence is not staged: ${path}")
    return 1
  fi
  return 0
}

if [[ "$staged_only" == false ]] && { [[ ! -f "$message_file" ]] || ! grep -Eq "^Workflow-Checkpoint:[[:space:]]+${change_id}[[:space:]]*$" "$message_file"; }; then
  failures+=("commit message lacks exact trailer: Workflow-Checkpoint: ${change_id}")
fi

tasks_path="openspec/changes/${change_id}/tasks.md"
verification_path="openspec/changes/${change_id}/verification.md"
status_path="workflow/state/status.md"
next_path="workflow/state/NEXT-SESSION.md"
registry_path="workflow/state/task-registry.md"

require_staged "$tasks_path" || true
require_staged "$verification_path" || true
require_staged "$status_path" || true
require_staged "$next_path" || true
require_staged "$registry_path" || true

report_path="$(printf '%s\n' "$staged_files" | grep -E "^workflow/state/reports/.*${change_id}.*\\.md$" | head -n 1 || true)"
if [[ -z "$report_path" ]]; then
  failures+=("required checkpoint evidence is not staged: workflow/state/reports/*${change_id}*.md")
fi

tasks_content="$(indexed "$tasks_path" || true)"
task_total="$(printf '%s\n' "$tasks_content" | grep -Ec '^- \[( |x|X)\]' || true)"
task_complete="$(printf '%s\n' "$tasks_content" | grep -Ec '^- \[[xX]\]' || true)"
if [[ "$task_total" -eq 0 ]]; then
  failures+=("staged tasks.md contains no checklist items")
elif [[ "$task_complete" -ge "$task_total" ]]; then
  failures+=("all staged tasks are complete; use the final change-done gate, not a checkpoint")
fi

verification_content="$(indexed "$verification_path" || true)"
for marker in '^- Completed: .+' '^- Focused checks: .+' '^- Open gates: .+' '^- Next: .+'; do
  if ! grep -Eq "$marker" <<< "$verification_content"; then
    failures+=("staged verification.md lacks checkpoint marker ${marker#^- }")
  fi
done

status_content="$(indexed "$status_path" || true)"
if ! awk -v change="$change_id" '
  $0 == "- Change: " change { seen=1; next }
  seen && $0 == "- Status: checkpointed" { found=1; exit }
  seen && /^## / { exit }
  END { exit(found ? 0 : 1) }
' <<< "$status_content"; then
  failures+=("staged status.md lacks a matching checkpointed entry")
fi

next_content="$(indexed "$next_path" || true)"
if ! awk -v change="$change_id" '
  index($0, "- " change ": ") == 1 { seen=1; next }
  seen && /^  Next: .+/ { found=1; exit }
  seen && /^- [^ ]/ { exit }
  END { exit(found ? 0 : 1) }
' <<< "$next_content"; then
  failures+=("staged NEXT-SESSION.md lacks the matching change and resume step")
fi

registry_content="$(indexed "$registry_path" || true)"
if ! awk -v change="$change_id" -v done="$task_complete" -v total="$task_total" '
  $0 == "### " change { seen=1; next }
  seen && $0 == "- Tasks: " done "/" total { found=1; exit }
  seen && /^### / { exit }
  END { exit(found ? 0 : 1) }
' <<< "$registry_content"; then
  failures+=("staged task-registry.md does not match the staged task count")
fi

if [[ -n "$report_path" ]]; then
  report_content="$(indexed "$report_path" || true)"
  for marker in "^- Change: ${change_id}$" '^- Completed: .+' '^- Focused checks: .+' '^- Open gates: .+' '^- Next: .+'; do
    if ! grep -Eq "$marker" <<< "$report_content"; then
      failures+=("staged ${report_path} lacks checkpoint marker ${marker#^- }")
    fi
  done
fi

if [[ ${#failures[@]} -gt 0 ]]; then
  echo "Checkpoint gate failed for ${change_id}:" >&2
  printf '%s\n' "${failures[@]}" | sed 's/^/- /' >&2
  exit 1
fi

if [[ "$quiet" == false ]]; then
  echo "Checkpoint gate passed for ${change_id}."
fi

exit 0
