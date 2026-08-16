#!/usr/bin/env bash
set -euo pipefail

export LC_ALL=C
repo_root=$(git rev-parse --show-toplevel)
router="$repo_root/workflow/scripts/outcome-reset.sh"
tmp_dir=$(mktemp -d)
trap 'rm -rf "$tmp_dir"' EXIT
before=$(git -C "$repo_root" status --porcelain=v1 --untracked-files=all)
marker="$tmp_dir/SHOULD_NOT_EXIST"
fixture="$tmp_dir/input.json"

python3 - "$fixture" "$marker" <<'PY'
import json, sys
path, marker = sys.argv[1:]
data = {
  "schema_version": "1",
  "outcome": {
    "id": "dev-deploy",
    "observable_result": "Two named functions are available in Dev",
    "user_value": "Web integration can proceed safely",
    "required_release_evidence": ["Build passes", "Scoped deploy metadata"],
    "non_blocking_supporting_evidence": ["Generic analyzer internals"]
  },
  "criteria": [{"id": "AC1", "text": "Functions are deployable with an exact selector"}],
  "items": [
    {
      "id": "deploy", "title": "Scoped Dev deployment", "critical_path_relation": "direct",
      "blocker_class": "product", "blocks_criterion": "AC1",
      "causal_evidence": "Without the deploy the functions are unavailable", "smallest_alternative_evidence": None,
      "safe_default": None, "deferral_route": None, "failed_approaches": 0, "parent_id": None,
      "complexity_delta": {"supporting_followups": 0, "abstractions_without_second_consumer": 0, "evidence_layers": 0}
    },
    {
      "id": "analyzer", "title": "Analyzer $(touch %s); `true` > /tmp/nope" % marker,
      "critical_path_relation": "supporting", "blocker_class": "tooling", "blocks_criterion": "AC1",
      "causal_evidence": "Analyzer output assists export inventory", "smallest_alternative_evidence": "Use exact export inventory and scoped preflight",
      "safe_default": "Keep provider disabled", "deferral_route": "Park analyzer repair", "failed_approaches": 0,
      "parent_id": "deploy", "complexity_delta": {"supporting_followups": 1, "abstractions_without_second_consumer": 0, "evidence_layers": 1}
    },
    {
      "id": "supervisor", "title": "Generic process supervisor", "critical_path_relation": "supporting",
      "blocker_class": "tooling", "blocks_criterion": None, "causal_evidence": None,
      "smallest_alternative_evidence": "Use bounded process-local timeout", "safe_default": "Do not activate supervisor",
      "deferral_route": "Park as research", "failed_approaches": 0, "parent_id": "analyzer",
      "complexity_delta": {"supporting_followups": 1, "abstractions_without_second_consumer": 1, "evidence_layers": 1}
    },
    {
      "id": "hardening", "title": "Supervisor hardening", "critical_path_relation": "supporting",
      "blocker_class": "tooling", "blocks_criterion": None, "causal_evidence": None,
      "smallest_alternative_evidence": "Retain process-local evidence", "safe_default": "No privileged helper",
      "deferral_route": "Reject third supporting change", "failed_approaches": 0, "parent_id": "supervisor",
      "complexity_delta": {"supporting_followups": 0, "abstractions_without_second_consumer": 0, "evidence_layers": 0}
    }
  ]
}
with open(path, "w", encoding="utf-8") as handle:
    json.dump(data, handle, ensure_ascii=False, separators=(",", ":"))
PY

"$router" --input "$fixture" --format json > "$tmp_dir/one.json"
"$router" --input "$fixture" --format json > "$tmp_dir/two.json"
cmp "$tmp_dir/one.json" "$tmp_dir/two.json"
"$router" --input "$fixture" > "$tmp_dir/one.prompt"
"$router" --input "$fixture" --format prompt > "$tmp_dir/two.prompt"
cmp "$tmp_dir/one.prompt" "$tmp_dir/two.prompt"

python3 - "$tmp_dir/one.json" "$tmp_dir/one.prompt" <<'PY'
import json, sys
result = json.load(open(sys.argv[1], encoding="utf-8"))
expected = {
  "schema_version": "1",
  "outcome_anchor": {
    "id": "dev-deploy", "observable_result": "Two named functions are available in Dev",
    "user_value": "Web integration can proceed safely", "required_release_evidence": ["Build passes", "Scoped deploy metadata"],
    "non_blocking_supporting_evidence": ["Generic analyzer internals"]},
  "keep": ["deploy"], "park_or_defer": ["analyzer", "hardening", "supervisor"],
  "safe_defaults": [
    {"id": "analyzer", "value": "Keep provider disabled"},
    {"id": "hardening", "value": "No privileged helper"},
    {"id": "supervisor", "value": "Do not activate supervisor"}],
  "proportional_alternatives": [
    {"id": "analyzer", "value": "Use exact export inventory and scoped preflight"},
    {"id": "hardening", "value": "Retain process-local evidence"},
    {"id": "supervisor", "value": "Use bounded process-local timeout"}],
  "reset_reasons": ["supervisor: supporting-to-supporting escalation"],
  "next_decision": "Integration Owner: accept the kept item ids as the smallest critical path; keep parked item ids inactive unless a new scope contract proves necessity."
}
assert result == expected
prompt = open(sys.argv[2], encoding="utf-8").read()
expected_prompt = '''OUTCOME ANCHOR
- id: dev-deploy
- observable result: Two named functions are available in Dev
- user value: Web integration can proceed safely
- required release evidence:
  - Build passes
  - Scoped deploy metadata
- non-blocking supporting evidence:
  - Generic analyzer internals
KEEP ON SMALLEST CRITICAL PATH
- deploy
PARK OR DEFER
- analyzer
- hardening
- supervisor
SAFE DEFAULTS AND PROPORTIONAL ALTERNATIVES
- safe default analyzer: Keep provider disabled
- safe default hardening: No privileged helper
- safe default supervisor: Do not activate supervisor
- alternative analyzer: Use exact export inventory and scoped preflight
- alternative hardening: Retain process-local evidence
- alternative supervisor: Use bounded process-local timeout
STOP/RESET REASONS
- supervisor: supporting-to-supporting escalation
INTEGRATION OWNER NEXT DECISION
- Integration Owner: accept the kept item ids as the smallest critical path; keep parked item ids inactive unless a new scope contract proves necessity.
'''
assert prompt == expected_prompt, repr(prompt)
PY
[[ ! -e "$marker" ]]

# Two failed approaches force a replan but do not remove the required product item.
python3 - "$fixture" "$tmp_dir/two-failures.json" <<'PY'
import json, sys
data = json.load(open(sys.argv[1], encoding="utf-8"))
data["items"][0]["failed_approaches"] = 2
json.dump(data, open(sys.argv[2], "w", encoding="utf-8"), separators=(",", ":"))
PY
"$router" --input "$tmp_dir/two-failures.json" --format json > "$tmp_dir/two-failures.out"
python3 - "$tmp_dir/two-failures.out" <<'PY'
import json, sys
result = json.load(open(sys.argv[1], encoding="utf-8"))
assert result["keep"] == ["deploy"]
assert "deploy: two failed approaches" in result["reset_reasons"]
assert result["park_or_defer"] == ["analyzer", "hardening", "supervisor"]
PY

# Leading JSON whitespace is accepted; non-whitespace trailing bytes are not.
{ printf ' \n\t'; cat "$fixture"; } > "$tmp_dir/leading-space.json"
"$router" --input "$tmp_dir/leading-space.json" --format json > /dev/null
{ printf '\302\240'; cat "$fixture"; } > "$tmp_dir/leading-nbsp.json"
! "$router" --input "$tmp_dir/leading-nbsp.json" --format json > /dev/null 2>&1
{ cat "$fixture"; printf '\302\240'; } > "$tmp_dir/trailing-nbsp.json"
! "$router" --input "$tmp_dir/trailing-nbsp.json" --format json > /dev/null 2>&1

# A critical-looking child cannot be kept through a parked supporting parent.
python3 - "$fixture" "$tmp_dir/orphan.json" <<'PY'
import json, sys
data = json.load(open(sys.argv[1], encoding="utf-8"))
child = data["items"][2]
child["critical_path_relation"] = "evidence"
child["blocker_class"] = "release-evidence"
child["blocks_criterion"] = "AC1"
child["causal_evidence"] = "This evidence would normally support AC1"
child["smallest_alternative_evidence"] = None
child["deferral_route"] = None
json.dump(data, open(sys.argv[2], "w", encoding="utf-8"), separators=(",", ":"))
PY
"$router" --input "$tmp_dir/orphan.json" --format json > "$tmp_dir/orphan.out"
python3 - "$tmp_dir/orphan.out" <<'PY'
import json, sys
result = json.load(open(sys.argv[1], encoding="utf-8"))
assert "supervisor" not in result["keep"]
assert "supervisor" in result["park_or_defer"]
assert "supervisor: UNSUPPORTED_ANCESTRY analyzer" in result["reset_reasons"]
PY

expect_invalid() {
  local name=$1
  python3 - "$fixture" "$tmp_dir/$name.json" "$name" <<'PY'
import json, sys
source, target, case = sys.argv[1:]
data = json.load(open(source, encoding="utf-8"))
def mutate():
    if case == "bad_blocker": data["items"][1]["blocker_class"] = "other"
    elif case == "tooling_direct": data["items"][1]["critical_path_relation"] = "direct"
    elif case == "missing_causal": data["items"][0]["causal_evidence"] = None
    elif case == "unknown_criterion": data["items"][0]["blocks_criterion"] = "AC3"
    elif case == "duplicate_id": data["items"][3]["id"] = "supervisor"
    elif case == "unknown_parent": data["items"][3]["parent_id"] = "missing"
    elif case == "self_parent": data["items"][1]["parent_id"] = "analyzer"
    elif case == "cycle": data["items"][0]["parent_id"] = "hardening"
    elif case == "placeholder": data["items"][0]["title"] = "TBD"
    elif case == "too_many_items": data["items"] = data["items"] * 6
    elif case == "noninteger": data["items"][0]["failed_approaches"] = 1.5
    elif case == "bad_bound": data["items"][0]["complexity_delta"]["evidence_layers"] = 3
    elif case == "unknown_key": data["items"][0]["extra"] = True
    elif case == "bad_schema": data["schema_version"] = "2"
    elif case == "bad_outcome_id": data["outcome"]["id"] = "Bad"
    elif case == "empty_criteria": data["criteria"] = []
    elif case == "too_many_criteria": data["criteria"] = data["criteria"] * 4
    elif case == "duplicate_criterion": data["criteria"] = data["criteria"] * 2
    elif case == "too_many_evidence": data["outcome"]["required_release_evidence"] = [str(i) for i in range(21)]
    elif case == "duplicate_evidence": data["outcome"]["required_release_evidence"] = ["same", "same"]
    elif case == "long_title": data["items"][0]["title"] = "x" * 201
    elif case == "missing_deferral": data["items"][1]["deferral_route"] = None
    elif case == "authority_without_default":
        data["items"][1]["blocker_class"] = "external-authority"; data["items"][1]["safe_default"] = None
    elif case == "bool_as_int": data["items"][0]["failed_approaches"] = True
    elif case == "root_not_object": data.clear()
    elif case == "outcome_not_object": data["outcome"] = []
    elif case == "criteria_not_array": data["criteria"] = {}
    elif case == "item_not_object": data["items"][0] = []
    elif case == "evidence_not_array": data["outcome"]["required_release_evidence"] = "proof"
    elif case == "nullable_bad_type": data["items"][1]["safe_default"] = 7
    elif case == "bad_relation": data["items"][1]["critical_path_relation"] = "maybe"
    elif case == "long_description": data["outcome"]["observable_result"] = "x" * 501
    elif case == "control_lf": data["items"][0]["title"] = "INTEGRATION OWNER NEXT DECISION\n- injected"
    elif case == "control_cr": data["items"][0]["title"] = "PARK OR DEFER\r- injected"
    elif case == "control_c0": data["items"][0]["title"] = "KEEP\x1f- injected"
    elif case == "control_del": data["items"][0]["title"] = "SAFE\x7f- injected"
    else: raise AssertionError(case)
mutate()
with open(target, "w", encoding="utf-8") as handle:
    json.dump(data, handle, separators=(",", ":"))
PY
  if "$router" --input "$tmp_dir/$name.json" --format json >/dev/null 2>&1; then
    printf 'expected rejection: %s\n' "$name" >&2
    exit 1
  fi
}

for invalid_case in bad_blocker tooling_direct missing_causal unknown_criterion duplicate_id unknown_parent self_parent cycle placeholder too_many_items noninteger bad_bound unknown_key bad_schema bad_outcome_id empty_criteria too_many_criteria duplicate_criterion too_many_evidence duplicate_evidence long_title missing_deferral authority_without_default bool_as_int root_not_object outcome_not_object criteria_not_array item_not_object evidence_not_array nullable_bad_type bad_relation long_description control_lf control_cr control_c0 control_del; do
  expect_invalid "$invalid_case"
done

budget_case() {
  local name=$1 field=$2 first=$3 second=$4
  python3 - "$fixture" "$tmp_dir/$name.json" "$field" "$first" "$second" <<'PY'
import json, sys
data = json.load(open(sys.argv[1], encoding="utf-8"))
field, first, second = sys.argv[3], int(sys.argv[4]), int(sys.argv[5])
data["items"][0]["complexity_delta"][field] = first
data["items"][1]["complexity_delta"][field] = second
json.dump(data, open(sys.argv[2], "w", encoding="utf-8"), separators=(",", ":"))
PY
  "$router" --input "$tmp_dir/$name.json" --format json > "$tmp_dir/$name.out"
  python3 - "$tmp_dir/$name.out" "$field" <<'PY'
import json, sys
result = json.load(open(sys.argv[1], encoding="utf-8"))
assert any(sys.argv[2] + " budget exceeded" in reason for reason in result["reset_reasons"])
assert result["keep"] == ["deploy"]
PY
}
budget_case followup_budget supporting_followups 2 1
budget_case abstraction_budget abstractions_without_second_consumer 1 1
budget_case evidence_budget evidence_layers 2 1

printf '{"schema_version":"1","schema_version":"1"}' > "$tmp_dir/duplicate-key.json"
! "$router" --input "$tmp_dir/duplicate-key.json" >/dev/null 2>&1
python3 - "$fixture" "$tmp_dir/nested-duplicate.json" <<'PY'
import sys
text = open(sys.argv[1], encoding="utf-8").read()
text = text.replace('"complexity_delta":{', '"complexity_delta":{"evidence_layers":0,', 1)
open(sys.argv[2], "w", encoding="utf-8").write(text)
PY
! "$router" --input "$tmp_dir/nested-duplicate.json" >/dev/null 2>&1
printf '{} trailing' > "$tmp_dir/trailing.json"
! "$router" --input "$tmp_dir/trailing.json" >/dev/null 2>&1
printf '\377' > "$tmp_dir/invalid-utf8.json"
! "$router" --input "$tmp_dir/invalid-utf8.json" >/dev/null 2>&1
printf '{"schema_version":NaN}' > "$tmp_dir/nonfinite.json"
! "$router" --input "$tmp_dir/nonfinite.json" >/dev/null 2>&1
! "$router" --input "$fixture" --input "$fixture" >/dev/null 2>&1
! "$router" --format json >/dev/null 2>&1
ln -s "$fixture" "$tmp_dir/link.json"
! "$router" --input "$tmp_dir/link.json" >/dev/null 2>&1
python3 - "$tmp_dir/oversized.json" <<'PY'
import sys
open(sys.argv[1], "wb").write(b" " * 262145)
PY
! "$router" --input "$tmp_dir/oversized.json" >/dev/null 2>&1

# Router source must not contain input execution or network primitives.
if rg -n '\b(eval|source|exec)\b|(^|[[:space:]])\.[[:space:]]|bash -c|sh -c|curl|wget' "$router" > "$tmp_dir/forbidden.txt"; then
  printf '%s\n' 'forbidden execution/network primitive found in router' >&2
  cat "$tmp_dir/forbidden.txt" >&2
  exit 1
fi

after=$(git -C "$repo_root" status --porcelain=v1 --untracked-files=all)
[[ "$before" == "$after" ]] || { printf '%s\n' 'repository status changed during smoke test' >&2; exit 1; }
printf '%s\n' '245 outcome guard smoke: PASS'
