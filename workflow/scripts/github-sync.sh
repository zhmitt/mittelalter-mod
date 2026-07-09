#!/usr/bin/env bash

set -euo pipefail

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$repo_root"

config_file=".github/project-sync.json"
if [[ ! -f "$config_file" ]]; then
  echo "Missing $config_file -- run the one-time GitHub Projects setup first (see design.md of github-project-sync)." >&2
  exit 1
fi
if ! command -v gh >/dev/null 2>&1; then
  echo "gh CLI not found." >&2
  exit 1
fi

owner="$(python3 -c "import json;print(json.load(open('$config_file'))['owner'])")"
project_number="$(python3 -c "import json;print(json.load(open('$config_file'))['project_number'])")"
repo="$(gh repo view --json nameWithOwner -q .nameWithOwner)"
project_id="$(gh project view "$project_number" --owner "$owner" --format json -q .id)"

fields_json="$(gh project field-list "$project_number" --owner "$owner" --format json)"
status_field_id="$(printf '%s' "$fields_json" | python3 -c "import json,sys; d=json.load(sys.stdin); print([f['id'] for f in d['fields'] if f['name']=='Status'][0])")"
change_id_field_id="$(printf '%s' "$fields_json" | python3 -c "import json,sys; d=json.load(sys.stdin); print([f['id'] for f in d['fields'] if f['name']=='Change ID'][0])")"

status_option_id() {
  printf '%s' "$fields_json" | python3 -c "
import json, sys
d = json.load(sys.stdin)
f = [x for x in d['fields'] if x['name'] == 'Status'][0]
opt = [o for o in f['options'] if o['name'] == '$1'][0]
print(opt['id'])
"
}

todo_option_id="$(status_option_id Todo)"
in_progress_option_id="$(status_option_id 'In Progress')"
done_option_id="$(status_option_id Done)"

map_phase_to_status_option() {
  case "$1" in
    draft|ready_for_design|ready_for_tasks) echo "$todo_option_id" ;;
    in_progress|ready_for_verify) echo "$in_progress_option_id" ;;
    ready_for_archive|archived) echo "$done_option_id" ;;
    *) echo "$todo_option_id" ;;
  esac
}

find_issue_number() {
  local change_id="$1"
  gh issue list --repo "$repo" --state all --search "\"openspec-change: ${change_id}\" in:body" \
    --json number,body --jq ".[] | select(.body | contains(\"<!-- openspec-change: ${change_id} -->\")) | .number" \
    | head -n1
}

find_item_id_for_issue() {
  local issue_number="$1"
  gh project item-list "$project_number" --owner "$owner" --format json --limit 200 \
    | python3 -c "
import json, sys
d = json.load(sys.stdin)
for item in d['items']:
    content = item.get('content') or {}
    if content.get('number') == ${issue_number}:
        print(item['id'])
        break
"
}

set_item_status() {
  local item_id="$1" option_id="$2"
  gh project item-edit --id "$item_id" --project-id "$project_id" \
    --field-id "$status_field_id" --single-select-option-id "$option_id" >/dev/null
}

set_item_change_id() {
  local item_id="$1" change_id="$2"
  gh project item-edit --id "$item_id" --project-id "$project_id" \
    --field-id "$change_id_field_id" --text "$change_id" >/dev/null
}

sync_change() {
  local change_dir="$1"
  local change_id
  change_id="$(basename "$change_dir")"

  local phase_json state
  phase_json="$(workflow/scripts/phase-status.sh --change "$change_id" --json)"
  state="$(printf '%s' "$phase_json" | python3 -c "import json,sys; print(json.load(sys.stdin)['state'])")"

  local issue_number
  issue_number="$(find_issue_number "$change_id")"

  if [[ -z "$issue_number" ]]; then
    local body created_url
    body="Tracks \`openspec/changes/${change_id}/\`. This issue is a generated visual projection of that change folder -- edit the change folder, not this issue.

<!-- openspec-change: ${change_id} -->"
    created_url="$(gh issue create --repo "$repo" --title "$change_id" --body "$body" --project "Mittelalter-Mod")"
    issue_number="${created_url##*/}"
    echo "Created issue #${issue_number} for ${change_id}"
  fi

  local item_id
  item_id="$(find_item_id_for_issue "$issue_number")"
  if [[ -z "$item_id" ]]; then
    item_id="$(gh project item-add "$project_number" --owner "$owner" \
      --url "https://github.com/${repo}/issues/${issue_number}" --format json -q .id)"
  fi

  set_item_status "$item_id" "$(map_phase_to_status_option "$state")"
  set_item_change_id "$item_id" "$change_id"

  echo "Synced ${change_id} -> issue #${issue_number}, state=${state}"
}

cmd="${1:-sync}"

case "$cmd" in
  sync)
    while IFS= read -r dir; do
      [[ -z "$dir" ]] && continue
      sync_change "$dir"
    done < <(find openspec/changes -mindepth 1 -maxdepth 1 -type d ! -name archive | sort)
    ;;
  archive)
    change_id="${2:?Usage: github-sync.sh archive <change-id>}"
    issue_number="$(find_issue_number "$change_id")"
    if [[ -z "$issue_number" ]]; then
      echo "No tracked issue found for ${change_id}" >&2
      exit 1
    fi
    gh issue close "$issue_number" --repo "$repo" >/dev/null
    item_id="$(find_item_id_for_issue "$issue_number")"
    if [[ -n "$item_id" ]]; then
      set_item_status "$item_id" "$done_option_id"
    fi
    echo "Archived ${change_id} -> closed issue #${issue_number}, status=Done"
    ;;
  *)
    echo "Usage: github-sync.sh [sync|archive <change-id>]" >&2
    exit 1
    ;;
esac
