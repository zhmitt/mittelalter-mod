#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "$script_dir/../.." && pwd)"

assert_contract() {
  local repo="$1"
  local agents="$repo/AGENTS.md"
  local handoff="$repo/workflow/templates/worker-handoff.md"
  local spec="$repo/openspec/specs/workflow-governance/spec.md"

  [[ -f "$agents" && -f "$handoff" && -f "$spec" ]]
  [[ "$(rg -c '^## Environment-scoped autonomy$' "$agents")" -eq 1 ]]
  rg -Fq 'environment-scoped authorization envelope' "$agents"
  rg -Fq 'never authorizes Production, IAM/secret or' "$agents"
  rg -Fq 'cannot expand a native sandbox, connector, or provider' "$agents"
  rg -Fq 'genuine human input or judgment' "$agents"
  rg -Fq 'terminally quiet' "$agents"
  rg -Fq 'scope/over-engineering audit is read-only' "$agents"
  rg -Fq '## Environment-scoped authorization envelope' "$handoff"
  rg -Fq 'Canonical target identity:' "$handoff"
  rg -Fq 'User-only inputs:' "$handoff"
  rg -Fq 'Terminal wait notification:' "$handoff"
  rg -Fq 'Scope-audit authority:' "$handoff"
  rg -Fq 'Requirement: Bounded non-production chains use environment-scoped authority' "$spec"
  rg -Fq 'Requirement: Human gates and external waits stay minimal' "$spec"
  rg -Fq 'SHALL preserve Production, IAM/secret, payment, public, irreversible,' "$spec"
  rg -Fq 'native platform approval boundaries' "$spec"
  rg -Fq 'scope/over-engineering audit SHALL be read-only' "$spec"
}

if [[ "${1:-}" == "--fixture" ]]; then
  fixture="$(mktemp -d)"
  trap 'rm -rf "$fixture"' EXIT
  mkdir -p "$fixture/workflow/templates" "$fixture/openspec/specs/workflow-governance"
  cp "$repo_root/AGENTS.md" "$fixture/AGENTS.md"
  cp "$repo_root/workflow/templates/worker-handoff.md" "$fixture/workflow/templates/worker-handoff.md"
  cp "$repo_root/openspec/specs/workflow-governance/spec.md" "$fixture/openspec/specs/workflow-governance/spec.md"
  git -C "$fixture" init -q
  assert_contract "$fixture"
  echo 'PASS: environment-scoped autonomy fixture'
  exit 0
fi

fleet_root="${1:-/Users/mschmitt/Documents/Git}"
expected_count="${ENVIRONMENT_SCOPED_AUTONOMY_EXPECTED_COUNT:-27}"
count=0
while IFS= read -r agents; do
  [[ -n "$agents" ]] || continue
  repo="${agents%/AGENTS.md}"
  git_dir="$(git -C "$repo" rev-parse --absolute-git-dir 2>/dev/null || true)"
  [[ -n "$git_dir" && "$git_dir" != *'/worktrees/'* ]] || continue
  assert_contract "$repo"
  count=$((count + 1))
done < <(rg -l '^## Inherited worker authority$' "$fleet_root" -g AGENTS.md | sort)

[[ "$count" -eq "$expected_count" ]]
echo "PASS: environment-scoped autonomy ($count repositories)"
