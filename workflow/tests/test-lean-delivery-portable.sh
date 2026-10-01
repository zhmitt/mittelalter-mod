#!/usr/bin/env bash

set -euo pipefail

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
fixture_root="$(mktemp -d)"
trap 'rm -rf "$fixture_root"' EXIT
fail() { echo "FAIL: $*" >&2; exit 1; }

for claim in '## Lean delivery authority' 'Change-Id: <change-id>' 'Only `active_write` consumes writing'; do
  grep -Fq "$claim" "$repo_root/AGENTS.md" || fail "missing governance claim: $claim"
done
must_word='MUST'
if grep -Fqi "Main Session **${must_word} delegate**" "$repo_root/AGENTS.md" ||
  grep -Fqi "${must_word} delegate each" "$repo_root/AGENTS.md"; then
  fail 'unconditional delegation wording remains in AGENTS.md'
fi
hard_gate_prefix='This gate is intentionally'
missing_agent_reason='no Plan or Explore'
if grep -Fqi "${hard_gate_prefix} hard" "$repo_root/.claude/hooks/gate-edit.sh" ||
  grep -Eiq "Reason:[[:space:]]+${missing_agent_reason}" "$repo_root/.claude/hooks/gate-edit.sh"; then
  fail 'edit hook still hard-gates solely on prior sub-agent evidence'
fi
grep -Fq '## Always-required compact core' "$repo_root/workflow/templates/worker-handoff.md" ||
  fail 'worker handoff lacks the actual compact core template'
for adapter in \
  .claude/commands/opsx/apply.md \
  .codex/skills/openspec-apply/SKILL.md \
  .gemini/skills/openspec-apply/SKILL.md; do
  grep -Fq 'compact working memory' "$repo_root/$adapter" || fail "adapter drift: $adapter"
done

mkdir -p "$fixture_root/openspec/changes/archive" "$fixture_root/workflow/scripts" "$fixture_root/workflow/state/reports"
cp "$repo_root/workflow/scripts/phase-status.sh" "$fixture_root/workflow/scripts/"
cp "$repo_root/workflow/scripts/tasks-sync.sh" "$fixture_root/workflow/scripts/"
chmod +x "$fixture_root/workflow/scripts/"*.sh
printf '# Status\n\n' > "$fixture_root/workflow/state/status.md"

if grep -Fq 'frontier-check.sh' "$fixture_root/workflow/scripts/phase-status.sh"; then
  cat > "$fixture_root/workflow/scripts/frontier-check.sh" <<'EOF'
#!/usr/bin/env bash
for dir in openspec/changes/*; do
  [[ -d "$dir" && "$(basename "$dir")" != archive ]] || continue
  printf '%s %s\n' "$(basename "$dir")" ready
done
EOF
  chmod +x "$fixture_root/workflow/scripts/frontier-check.sh"
fi

write_change() {
  local id="$1"
  mkdir -p "$fixture_root/openspec/changes/$id/specs/workflow-governance"
  printf '# Proposal\n' > "$fixture_root/openspec/changes/$id/proposal.md"
  printf '# Design\n' > "$fixture_root/openspec/changes/$id/design.md"
  printf '## Requirement: Fixture\nThe fixture SHALL pass.\n' > "$fixture_root/openspec/changes/$id/specs/workflow-governance/spec.md"
  printf '# Tasks\n\n- [x] Preserve slice.\n- [ ] Continue when scheduled.\n' > "$fixture_root/openspec/changes/$id/tasks.md"
}

for id in active-slice verify-slice external-slice user-slice parked-slice legacy-slice; do write_change "$id"; done
cat >> "$fixture_root/workflow/state/status.md" <<'EOF'
- Change: active-slice
- Orchestration: active_write
- Change: verify-slice
- Orchestration: ready_for_verify
- Change: external-slice
- Orchestration: external_wait
- Change: user-slice
- Orchestration: user_action_pending
- Change: parked-slice
- Orchestration: parked
EOF

assert_state() {
  local id="$1" expected="$2" actual
  actual="$(cd "$fixture_root" && workflow/scripts/phase-status.sh --change "$id" | awk -F': ' '/^State:/ { print $2 }')"
  [[ "$actual" == "$expected" ]] || fail "$id was $actual, expected $expected"
}
assert_state active-slice active_write
assert_state verify-slice ready_for_verify
assert_state external-slice external_wait
assert_state user-slice user_action_pending
assert_state parked-slice parked
assert_state legacy-slice parked

registry="$(cd "$fixture_root" && workflow/scripts/tasks-sync.sh --dry-run)"
for heading in '## Active Delivery' '## Verification Queue' '## External Wait' '## User Action Pending' '## Parked'; do
  grep -Fq "$heading" <<< "$registry" || fail "registry omitted $heading"
done

bash -n "$repo_root/.git-hooks/commit-msg" \
  "$repo_root/workflow/scripts/checkpoint-commit-check.sh" \
  "$repo_root/workflow/scripts/milestone-sync.sh"
echo 'PASS: portable lean delivery fixture'
