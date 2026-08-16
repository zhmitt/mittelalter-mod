#!/usr/bin/env bash
set -euo pipefail

usage() {
  echo "Usage: outcome-reset.sh --outcome <text> [--delivered <text>] [--active <text>] [--blockers <text>] [--safe-defaults <text>] [--failed-approaches <0|1|2>] [--supporting-depth <n>]" >&2
  exit 2
}

outcome=""
delivered="none recorded"
active="none recorded"
blockers="none recorded"
safe_defaults="none recorded"
failed_approaches=0
supporting_depth=0

while [[ $# -gt 0 ]]; do
  case "$1" in
    --outcome) [[ $# -ge 2 ]] || usage; outcome="$2"; shift 2 ;;
    --delivered) [[ $# -ge 2 ]] || usage; delivered="$2"; shift 2 ;;
    --active) [[ $# -ge 2 ]] || usage; active="$2"; shift 2 ;;
    --blockers) [[ $# -ge 2 ]] || usage; blockers="$2"; shift 2 ;;
    --safe-defaults) [[ $# -ge 2 ]] || usage; safe_defaults="$2"; shift 2 ;;
    --failed-approaches) [[ $# -ge 2 ]] || usage; failed_approaches="$2"; shift 2 ;;
    --supporting-depth) [[ $# -ge 2 ]] || usage; supporting_depth="$2"; shift 2 ;;
    *) usage ;;
  esac
done

[[ -n "$outcome" ]] || usage
[[ "$failed_approaches" =~ ^[0-2]$ ]] || usage
[[ "$supporting_depth" =~ ^[0-9]+$ ]] || usage

decision="review_scope"
if (( failed_approaches >= 2 || supporting_depth >= 2 )); then
  decision="reset_required"
fi

cat <<EOF
# Outcome reset

Outcome anchor: $outcome
Delivered user value: $delivered
Active changes: $active
Claimed blockers: $blockers
Safe defaults: $safe_defaults
Failed approaches: $failed_approaches
Supporting depth: $supporting_depth
Decision: $decision

## Required routing

For each active change classify:
- critical path: directly closes an acceptance criterion;
- safety: prevents a concrete reproduced high-risk harm;
- evidence: supplies mandatory evidence with no smaller proportional substitute;
- supporting: tooling/research that defaults to defer or park.

Keep active only the smallest critical path. For every other item record the
exact blocked criterion and causal evidence, or route it to a safe default,
non-blocking follow-up, parked research, merge, or closure. Do not create a new
blocking change from this reset alone.
EOF
