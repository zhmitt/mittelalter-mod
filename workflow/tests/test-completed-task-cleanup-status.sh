#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"; h="$root/workflow/scripts/completed-task-cleanup-status.sh"
has(){ [[ "$1" == *"$2"* ]] || { echo "Expected $2" >&2; exit 1; }; }
e=(--worker-kind change-task --worker-final yes --acceptance-tests-handed-off yes --checkpoint-commit abc123 --integrated-or-preserved yes --blocker no --destination-thread-id thread-1 --worktree-exact-owned yes --worktree-orphan no --worktree-clean yes --worktree-needed-for-diagnosis no --worktree-active-owner no)
o="$("$h" "${e[@]}")"; has "$o" 'Task cleanup: archive_task'; has "$o" 'Worktree release: eligible'
tb(){ o="$("$h" "${e[@]}" "$@")"; has "$o" 'Task cleanup: blocked_manual_cleanup'; }; tb --worker-final no; tb --acceptance-tests-handed-off no; tb --checkpoint-commit ' '; tb --integrated-or-preserved no; tb --blocker yes; tb --destination-thread-id '' --thread-id ''
o="$("$h" "${e[@]}" --destination-thread-id '' --thread-id '' --client-thread-id queued)"; has "$o" client_thread_id_without_thread_id
o="$("$h" "${e[@]}" --worker-kind subagent)"; has "$o" 'Task cleanup: not_applicable_subagent'
wb(){ o="$("$h" "${e[@]}" "$@")"; has "$o" 'Worktree release: blocked_manual_cleanup'; }; wb --worktree-exact-owned no; wb --worktree-orphan yes; wb --worktree-clean no; wb --worktree-needed-for-diagnosis yes; wb --worktree-active-owner yes
o="$("$h" "${e[@]}" --worktree-clean no)"; has "$o" 'Task cleanup: archive_task'; has "$o" 'Worktree release: blocked_manual_cleanup'; echo 'PASS: completed task cleanup lifecycle'

