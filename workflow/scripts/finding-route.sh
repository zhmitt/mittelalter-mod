#!/usr/bin/env bash
set -euo pipefail
blocks=""; critical=""; evidence=""; prioritized=""; duplicate="no"; cosmetic="no"
while [[ $# -gt 0 ]]; do
  case "$1" in
    --blocks-acceptance) blocks="${2:-}"; shift 2 ;;
    --critical-risk) critical="${2:-}"; shift 2 ;;
    --concrete-evidence) evidence="${2:-}"; shift 2 ;;
    --prioritized) prioritized="${2:-}"; shift 2 ;;
    --duplicate) duplicate="${2:-}"; shift 2 ;;
    --cosmetic) cosmetic="${2:-}"; shift 2 ;;
    *) echo "Unknown argument: $1" >&2; exit 2 ;;
  esac
done
for name in blocks critical evidence prioritized; do [[ -n "${!name}" ]] || { echo "Missing required input: $name" >&2; exit 2; }; done
for name in blocks critical evidence prioritized duplicate cosmetic; do value="${!name}"; [[ "$value" == yes || "$value" == no ]] || { echo "Invalid value for $name: expected yes or no" >&2; exit 2; }; done
if [[ "$evidence" == no && ( "$blocks" == yes || "$critical" == yes || "$prioritized" == yes ) ]]; then echo "Conflicting inputs: route requires concrete evidence" >&2; exit 2; fi
if [[ ( "$duplicate" == yes || "$cosmetic" == yes ) && ( "$blocks" == yes || "$critical" == yes || "$prioritized" == yes ) ]]; then echo "Conflicting inputs: duplicate/cosmetic cannot be blocking, critical, or prioritized" >&2; exit 2; fi
if [[ "$duplicate" == yes || "$cosmetic" == yes || "$evidence" == no ]]; then echo discard_or_deduplicate
elif [[ "$blocks" == yes ]]; then echo minimal_scope_checkpoint
elif [[ "$critical" == yes ]]; then echo urgent_separate_task
elif [[ "$prioritized" == yes ]]; then echo bounded_follow_up
else echo park_in_handoff; fi
