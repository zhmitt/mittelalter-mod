#!/usr/bin/env bash
set -euo pipefail

export LC_ALL=C

usage() {
  printf '%s\n' 'usage: outcome-reset.sh --input FILE [--format prompt|json]' >&2
  exit 64
}

input=''
format='prompt'
seen_format=0
while (($#)); do
  case "$1" in
    --input)
      [[ -z "$input" && $# -ge 2 ]] || usage
      input=$2
      shift 2
      ;;
    --format)
      [[ $seen_format -eq 0 && $# -ge 2 ]] || usage
      format=$2
      seen_format=1
      shift 2
      ;;
    *) usage ;;
  esac
done
[[ -n "$input" && ( "$format" == prompt || "$format" == json ) ]] || usage
[[ -f "$input" && ! -L "$input" ]] || { printf '%s\n' 'error: input must be a regular, non-symlink file' >&2; exit 66; }

python3 - "$input" "$format" <<'PY'
import json
import os
import re
import stat
import sys

path, output_format = sys.argv[1:]

class Invalid(ValueError):
    pass

def pairs(values):
    out = {}
    for key, value in values:
        if key in out:
            raise Invalid(f"duplicate key: {key}")
        out[key] = value
    return out

def fail(message):
    print(f"error: {message}", file=sys.stderr)
    raise SystemExit(65)

try:
    flags = os.O_RDONLY | getattr(os, "O_NOFOLLOW", 0)
    fd = os.open(path, flags)
    try:
        info = os.fstat(fd)
        if not stat.S_ISREG(info.st_mode):
            raise Invalid("input must be a regular file")
        if info.st_size > 262144:
            raise Invalid("input exceeds 262144-byte limit")
        raw = os.read(fd, 262145)
        if len(raw) > 262144:
            raise Invalid("input exceeds 262144-byte limit")
    finally:
        os.close(fd)
    text = raw.decode("utf-8", "strict")
    decoder = json.JSONDecoder(object_pairs_hook=pairs, parse_constant=lambda value: (_ for _ in ()).throw(Invalid(f"non-finite number: {value}")))
    leading = 0
    while leading < len(text) and text[leading] in " \t\r\n":
        leading += 1
    value, end = decoder.raw_decode(text, leading)
    if any(char not in " \t\r\n" for char in text[end:]):
        raise Invalid("trailing content")
except (OSError, UnicodeError, json.JSONDecodeError, Invalid) as error:
    fail(str(error))

PLACEHOLDER = re.compile(r"^(?:todo|tbd|pending|unknown|n/a|none)$", re.I)
ID = re.compile(r"^[a-z][a-z0-9-]{0,63}$")
AC = re.compile(r"^AC[1-3]$")

def exact(obj, keys, where):
    if type(obj) is not dict:
        raise Invalid(f"{where} must be an object")
    actual, expected = set(obj), set(keys)
    if actual != expected:
        raise Invalid(f"{where} keys invalid: missing={sorted(expected-actual)} unknown={sorted(actual-expected)}")

def string(value, where, maximum, pattern=None):
    if type(value) is not str or not (1 <= len(value) <= maximum) or not value.strip():
        raise Invalid(f"{where} must be a non-empty string of at most {maximum} characters")
    trimmed = value.strip()
    if any(ord(character) < 32 or ord(character) == 127 for character in value):
        raise Invalid(f"{where} contains a control character")
    if PLACEHOLDER.fullmatch(trimmed) or ("<" in value and ">" in value):
        raise Invalid(f"{where} contains a placeholder")
    if pattern and not pattern.fullmatch(value):
        raise Invalid(f"{where} has invalid format")
    return value

def nullable(value, where, maximum=500):
    return None if value is None else string(value, where, maximum)

def integer(value, where, low=0, high=2):
    if type(value) is not int or not low <= value <= high:
        raise Invalid(f"{where} must be an integer from {low} through {high}")
    return value

try:
    exact(value, ["schema_version", "outcome", "criteria", "items"], "root")
    if value["schema_version"] != "1":
        raise Invalid("schema_version must equal 1")
    outcome = value["outcome"]
    exact(outcome, ["id", "observable_result", "user_value", "required_release_evidence", "non_blocking_supporting_evidence"], "outcome")
    string(outcome["id"], "outcome.id", 64, ID)
    string(outcome["observable_result"], "outcome.observable_result", 500)
    string(outcome["user_value"], "outcome.user_value", 500)
    for field in ("required_release_evidence", "non_blocking_supporting_evidence"):
        entries = outcome[field]
        if type(entries) is not list or len(entries) > 20:
            raise Invalid(f"outcome.{field} must contain zero to twenty strings")
        for index, entry in enumerate(entries):
            string(entry, f"outcome.{field}[{index}]", 300)
        if len(entries) != len(set(entries)):
            raise Invalid(f"outcome.{field} contains duplicates")
    criteria = value["criteria"]
    if type(criteria) is not list or not 1 <= len(criteria) <= 3:
        raise Invalid("criteria must contain one to three objects")
    criterion_ids = set()
    for index, criterion in enumerate(criteria):
        exact(criterion, ["id", "text"], f"criteria[{index}]")
        cid = string(criterion["id"], f"criteria[{index}].id", 3, AC)
        string(criterion["text"], f"criteria[{index}].text", 500)
        if cid in criterion_ids:
            raise Invalid(f"duplicate criterion id: {cid}")
        criterion_ids.add(cid)
    items = value["items"]
    if type(items) is not list or len(items) > 20:
        raise Invalid("items must contain zero to twenty objects")
    item_keys = ["id", "title", "critical_path_relation", "blocker_class", "blocks_criterion", "causal_evidence", "smallest_alternative_evidence", "safe_default", "deferral_route", "failed_approaches", "parent_id", "complexity_delta"]
    relations = {"direct", "safety", "evidence", "supporting"}
    blockers = {"product", "safety-security", "release-evidence", "tooling", "external-authority"}
    by_id = {}
    for index, item in enumerate(items):
        exact(item, item_keys, f"items[{index}]")
        iid = string(item["id"], f"items[{index}].id", 64, ID)
        if iid in by_id:
            raise Invalid(f"duplicate item id: {iid}")
        string(item["title"], f"items[{index}].title", 200)
        if item["critical_path_relation"] not in relations:
            raise Invalid(f"{iid}.critical_path_relation is invalid")
        if item["blocker_class"] not in blockers:
            raise Invalid(f"{iid}.blocker_class is invalid")
        nullable(item["causal_evidence"], f"{iid}.causal_evidence")
        nullable(item["smallest_alternative_evidence"], f"{iid}.smallest_alternative_evidence")
        nullable(item["safe_default"], f"{iid}.safe_default")
        nullable(item["deferral_route"], f"{iid}.deferral_route")
        integer(item["failed_approaches"], f"{iid}.failed_approaches")
        exact(item["complexity_delta"], ["supporting_followups", "abstractions_without_second_consumer", "evidence_layers"], f"{iid}.complexity_delta")
        for field in item["complexity_delta"]:
            integer(item["complexity_delta"][field], f"{iid}.complexity_delta.{field}")
        by_id[iid] = item
    for iid, item in by_id.items():
        criterion = item["blocks_criterion"]
        if criterion is not None and (type(criterion) is not str or criterion not in criterion_ids):
            raise Invalid(f"{iid}.blocks_criterion is unknown")
        parent = item["parent_id"]
        if parent is not None and (type(parent) is not str or parent not in by_id or parent == iid):
            raise Invalid(f"{iid}.parent_id is invalid")
        relation, blocker = item["critical_path_relation"], item["blocker_class"]
        if relation == "direct" and blocker != "product":
            raise Invalid(f"{iid}: direct relation requires product blocker class")
        if relation == "safety" and blocker != "safety-security":
            raise Invalid(f"{iid}: safety relation requires safety-security blocker class")
        if relation == "evidence" and blocker not in {"release-evidence", "tooling"}:
            raise Invalid(f"{iid}: evidence relation requires release-evidence or justified tooling blocker class")
        if relation != "supporting" and (criterion is None or item["causal_evidence"] is None):
            raise Invalid(f"{iid}: critical-path claim requires exact criterion and causal evidence")
        if blocker == "tooling":
            if relation not in {"supporting", "evidence"}:
                raise Invalid(f"{iid}: tooling may only be supporting or justified release evidence")
            if relation == "supporting" and (item["smallest_alternative_evidence"] is None or item["deferral_route"] is None):
                raise Invalid(f"{iid}: supporting tooling requires alternative evidence and deferral route")
            if relation == "evidence" and item["smallest_alternative_evidence"] is not None:
                raise Invalid(f"{iid}: blocking tooling cannot have a smaller alternative evidence route")
        if relation == "supporting" and item["deferral_route"] is None:
            raise Invalid(f"{iid}: supporting work requires a deferral route")
        if blocker == "external-authority" and (item["safe_default"] is None or item["deferral_route"] is None):
            raise Invalid(f"{iid}: external authority requires safe default and deferral route")
    for iid in by_id:
        seen = set()
        current = iid
        while current is not None:
            if current in seen:
                raise Invalid("parent cycle detected")
            seen.add(current)
            current = by_id[current]["parent_id"]
except Invalid as error:
    fail(str(error))

children = {iid: [] for iid in by_id}
for iid, item in by_id.items():
    if item["parent_id"] is not None:
        children[item["parent_id"]].append(iid)

reset_roots = set()
reasons = []
for iid in sorted(by_id):
    item = by_id[iid]
    parent = by_id.get(item["parent_id"])
    grandparent = by_id.get(parent["parent_id"]) if parent else None
    if parent and parent["critical_path_relation"] == "supporting" and item["critical_path_relation"] == "supporting" and not (grandparent and grandparent["critical_path_relation"] == "supporting"):
        reset_roots.add(iid)
        reasons.append(f"{iid}: supporting-to-supporting escalation")
    if item["failed_approaches"] >= 2:
        reasons.append(f"{iid}: two failed approaches")
        if item["critical_path_relation"] == "supporting":
            reset_roots.add(iid)
        else:
            descendants = list(children[iid])
            while descendants:
                child = descendants.pop()
                if by_id[child]["critical_path_relation"] == "supporting":
                    reset_roots.add(child)
                descendants.extend(children[child])

totals = {key: sum(item["complexity_delta"][key] for item in by_id.values()) for key in ("supporting_followups", "abstractions_without_second_consumer", "evidence_layers")}
limits = {"supporting_followups": 2, "abstractions_without_second_consumer": 1, "evidence_layers": 2}
for key in limits:
    if totals[key] > limits[key]:
        reasons.append(f"outcome: {key} budget exceeded ({totals[key]}>{limits[key]})")
        reset_roots.update(iid for iid, item in by_id.items() if item["critical_path_relation"] == "supporting")

reset = set()
stack = list(reset_roots)
while stack:
    iid = stack.pop()
    if iid not in reset:
        reset.add(iid)
        stack.extend(children[iid])

base_keep = {
    iid for iid, item in by_id.items()
    if iid not in reset and item["critical_path_relation"] in {"direct", "safety", "evidence"}
}
keep_cache = {}
def ancestry_kept(iid):
    if iid in keep_cache:
        return keep_cache[iid]
    parent = by_id[iid]["parent_id"]
    keep_cache[iid] = iid in base_keep and (parent is None or ancestry_kept(parent))
    return keep_cache[iid]

kept, parked = [], []
for iid in sorted(by_id):
    if ancestry_kept(iid):
        kept.append(iid)
    else:
        parked.append(iid)
        parent = by_id[iid]["parent_id"]
        if iid in base_keep and parent is not None and not ancestry_kept(parent):
            reasons.append(f"{iid}: UNSUPPORTED_ANCESTRY {parent}")

defaults = []
alternatives = []
for iid in parked:
    item = by_id[iid]
    if item["safe_default"] is not None:
        defaults.append({"id": iid, "value": item["safe_default"]})
    if item["smallest_alternative_evidence"] is not None:
        alternatives.append({"id": iid, "value": item["smallest_alternative_evidence"]})

decision = "Integration Owner: accept the kept item ids as the smallest critical path; keep parked item ids inactive unless a new scope contract proves necessity."
result = {
    "schema_version": "1",
    "outcome_anchor": {key: outcome[key] for key in ("id", "observable_result", "user_value", "required_release_evidence", "non_blocking_supporting_evidence")},
    "keep": kept,
    "park_or_defer": parked,
    "safe_defaults": defaults,
    "proportional_alternatives": alternatives,
    "reset_reasons": sorted(set(reasons)),
    "next_decision": decision,
}

if output_format == "json":
    print(json.dumps(result, ensure_ascii=False, separators=(",", ":")))
else:
    def lines(values):
        return [f"- {value}" for value in values] or ["- (none)"]
    output = [
        "OUTCOME ANCHOR",
        f"- id: {outcome['id']}",
        f"- observable result: {outcome['observable_result']}",
        f"- user value: {outcome['user_value']}",
        "- required release evidence:",
    ]
    output.extend([f"  - {entry}" for entry in outcome["required_release_evidence"]] or ["  - (none)"])
    output.append("- non-blocking supporting evidence:")
    output.extend([f"  - {entry}" for entry in outcome["non_blocking_supporting_evidence"]] or ["  - (none)"])
    output.extend(["KEEP ON SMALLEST CRITICAL PATH", *lines(kept), "PARK OR DEFER", *lines(parked)])
    output.append("SAFE DEFAULTS AND PROPORTIONAL ALTERNATIVES")
    output.extend([f"- safe default {entry['id']}: {entry['value']}" for entry in defaults] or ["- safe defaults: (none)"])
    output.extend([f"- alternative {entry['id']}: {entry['value']}" for entry in alternatives] or ["- proportional alternatives: (none)"])
    output.extend(["STOP/RESET REASONS", *lines(sorted(set(reasons))), "INTEGRATION OWNER NEXT DECISION", f"- {decision}"])
    print("\n".join(output))
PY
