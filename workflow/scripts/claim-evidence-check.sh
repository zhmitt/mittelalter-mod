#!/usr/bin/env bash

# claim-evidence-check.sh — checks a Markdown file for claims without evidence.
#
# Usage: claim-evidence-check.sh <file-path> [--quiet]
#
# Exit 0 if all claims have evidence (or no claims found).
# Exit 1 if any claims lack evidence, printing the offending lines.
#
# --quiet suppresses OK output; error output is always shown.

set -euo pipefail

# ---------------------------------------------------------------------------
# Argument parsing
# ---------------------------------------------------------------------------

file_path=""
quiet=false

while [[ $# -gt 0 ]]; do
  case "$1" in
    --quiet)
      quiet=true
      shift
      ;;
    -*)
      echo "Unknown option: $1" >&2
      exit 1
      ;;
    *)
      if [[ -n "$file_path" ]]; then
        echo "Unexpected argument: $1" >&2
        exit 1
      fi
      file_path="$1"
      shift
      ;;
  esac
done

if [[ -z "$file_path" ]]; then
  echo "Usage: claim-evidence-check.sh <file-path> [--quiet]" >&2
  exit 1
fi

if [[ ! -f "$file_path" ]]; then
  echo "File not found: $file_path" >&2
  exit 1
fi

# ---------------------------------------------------------------------------
# Strip code blocks and HTML comments from input before analysis.
# ---------------------------------------------------------------------------

tmpfile="$(mktemp /tmp/claim-evidence-check.XXXXXX)"
trap 'rm -f "$tmpfile"' EXIT

awk '
  /^```/ {
    in_code = !in_code
    print ""
    next
  }
  in_code { print ""; next }
  /<!--.*-->/ { print ""; next }
  { print }
' "$file_path" > "$tmpfile"

# ---------------------------------------------------------------------------
# Pattern definitions
# ---------------------------------------------------------------------------

claim_pattern='(^|[^a-zA-Z])(implemented|implementiert|fixed|behoben|deployed|ausgerollt|live|tested|getestet|verified|verifiziert|ready|fertig|done|complete|abgeschlossen)([^a-zA-Z]|$)'

evidence_patterns=(
  '[a-zA-Z_][a-zA-Z0-9_.-]*\.(test|spec)\.(ts|tsx|js|jsx)'
  "describe\(['\"]"
  "it\(['\"]"
  '\b[0-9a-f]{7,40}\b'
  'packages/[^ :]+:[0-9]+'
  'https?://'
  'exit [0-9]+'
  'exit code [0-9]+'
  '`[a-z][a-z0-9 _-]+`'
)

evidence_regex="$(printf '%s|' "${evidence_patterns[@]}")"
evidence_regex="${evidence_regex%|}"

# ---------------------------------------------------------------------------
# Main check
# ---------------------------------------------------------------------------

total_lines="$(wc -l < "$tmpfile" | tr -d ' ')"
claims_without_evidence=()

while IFS= read -r line_entry; do
  lineno="${line_entry%%:*}"
  content="${line_entry#*:}"

  ctx_start=$(( lineno - 2 ))
  ctx_end=$(( lineno + 2 ))
  [[ "$ctx_start" -lt 1 ]] && ctx_start=1
  [[ "$ctx_end" -gt "$total_lines" ]] && ctx_end="$total_lines"

  context_block="$(awk "NR>=${ctx_start} && NR<=${ctx_end}" "$tmpfile")"

  if echo "$context_block" | grep -qiE "$evidence_regex" 2>/dev/null; then
    continue
  fi

  original_line="$(awk "NR==${lineno}" "$file_path")"
  claims_without_evidence+=("  line ${lineno}: ${original_line}")

done < <(grep -niE "$claim_pattern" "$tmpfile" || true)

# ---------------------------------------------------------------------------
# Output
# ---------------------------------------------------------------------------

if [[ ${#claims_without_evidence[@]} -eq 0 ]]; then
  [[ "$quiet" == false ]] && echo "claim-evidence-check OK: ${file_path}"
  exit 0
fi

echo "claim-evidence-check FAIL: ${file_path}" >&2
echo "Claims without evidence (${#claims_without_evidence[@]}):" >&2
for item in "${claims_without_evidence[@]}"; do
  echo "$item" >&2
done
exit 1
