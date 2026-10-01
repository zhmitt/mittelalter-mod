#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
route="$root/workflow/scripts/finding-route.sh"; policy="$root/AGENTS.md"; handoff="$root/workflow/templates/worker-handoff.md"
grep -Fq 'Shared focus and orchestration baseline' "$policy" || grep -Fq 'Focus and Scope Control' "$policy" || grep -Fq 'Session Contract' "$policy"
grep -Fq 'Record no more than three.' "$handoff"
base=(--blocks-acceptance no --critical-risk no --concrete-evidence yes --prioritized no)
[[ "$("$route" "${base[@]}")" == park_in_handoff ]]
[[ "$("$route" --blocks-acceptance yes --critical-risk no --concrete-evidence yes --prioritized no)" == minimal_scope_checkpoint ]]
[[ "$("$route" --blocks-acceptance no --critical-risk yes --concrete-evidence yes --prioritized no)" == urgent_separate_task ]]
[[ "$(grep -Ec '^[1-3]\\. Evidence:' "$handoff")" == 3 ]]
echo 'PASS: focus governance baseline'

