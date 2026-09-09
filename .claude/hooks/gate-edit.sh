#!/usr/bin/env bash
# PreToolUse advisory for Edit | Write | MultiEdit | NotebookEdit.
# Code edits are classified so missing Plan/Explore evidence can be surfaced,
# but delegation is benefit-conditioned and never a universal edit prerequisite.
# Markdown, configuration, OpenSpec, workflow, adapter, and hook files remain
# outside this advisory.
#
# CLAUDE_HOOKS_OFF=1 remains observable for compatibility and records a bypass
# marker, although missing delegation evidence no longer blocks an edit.
#
# Exit code: 0 → allow.

set -euo pipefail

repo_root="${CLAUDE_PROJECT_DIR:-$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)}"
evidence_dir="$repo_root/.workflow-evidence"
agents_log="$evidence_dir/agents.jsonl"
override_log="$evidence_dir/overrides.jsonl"
mkdir -p "$evidence_dir"

# Classify the file_path. classify outputs "<verdict>\t<rel_path>".
verdict_line=$(python3 "$repo_root/.claude/hooks/gate-edit.py" "$repo_root" 2>/dev/null || echo "allow\t")
verdict="${verdict_line%%	*}"
rel_path="${verdict_line#*	}"

if [[ "$verdict" != "gate" ]]; then
  exit 0
fi

# Override path.
if [[ "${CLAUDE_HOOKS_OFF:-0}" == "1" ]]; then
  ts=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
  esc_path=$(printf '%s' "$rel_path" | python3 -c 'import json,sys; print(json.dumps(sys.stdin.read())[1:-1], end="")')
  printf '{"ts":"%s","gate":"edit","file":"%s","reason":"CLAUDE_HOOKS_OFF"}\n' \
    "$ts" "$esc_path" >> "$override_log"
  exit 0
fi

# Lookback: 4 h (240 min). Plan/Explore evidence is useful telemetry only.
allowed=$(python3 "$repo_root/.claude/hooks/check-agents-log.py" "$agents_log" 240 Plan Explore 2>/dev/null || echo 0)

if [[ "$allowed" != "1" ]]; then
  cat >&2 <<EOF
ADVISORY from .claude/hooks/gate-edit.sh

File:    $rel_path
Notice:  no recent Plan or Explore sub-agent evidence was found.
         Continue locally when the slice is bounded; delegate only when useful
         parallel ownership, specialized capability, or independent judgment
         materially benefits the declared outcome.
EOF
fi

exit 0
