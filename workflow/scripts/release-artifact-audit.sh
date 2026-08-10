#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "$0")/../.." && pwd)"
artifact="${1:-}"

if [[ -z "$artifact" ]]; then
  shopt -s nullglob
  candidates=("$repo_root"/build/libs/mittelalter-*.jar)
  shopt -u nullglob
  if [[ ${#candidates[@]} -ne 1 ]]; then
    echo "release-artifact-audit: expected exactly one build/libs/mittelalter-*.jar, found ${#candidates[@]}" >&2
    exit 2
  fi
  artifact="${candidates[0]}"
elif [[ "$artifact" != /* ]]; then
  artifact="$repo_root/$artifact"
fi

if [[ ! -f "$artifact" ]]; then
  echo "release-artifact-audit: artifact not found: $artifact" >&2
  exit 2
fi

entry_file="$(mktemp "${TMPDIR:-/tmp}/mittelalter-jar-entries.XXXXXX")"
trap 'rm -f "$entry_file"' EXIT
LC_ALL=C unzip -Z1 "$artifact" | LC_ALL=C sort > "$entry_file"

required_entries=(
  "META-INF/MANIFEST.MF"
  "META-INF/neoforge.mods.toml"
  "de/mittelalter/MittelalterMod.class"
  "de/mittelalter/registry/ModBlocks.class"
  "de/mittelalter/registry/ModItems.class"
  "de/mittelalter/soldier/SoldierEntity.class"
  "de/mittelalter/tournament/TournamentManager.class"
  "de/mittelalter/camelot/CamelotSavedData.class"
  "de/mittelalter/role/RoleSavedData.class"
  "assets/mittelalter/lang/en_us.json"
  "assets/mittelalter/lang/de_de.json"
  "assets/mittelalter/items/longsword.json"
  "assets/mittelalter/items/command_baton.json"
  "assets/mittelalter/items/tournament_standard.json"
  "assets/mittelalter/items/camelot_charter.json"
  "assets/mittelalter/items/arthur_signet.json"
  "assets/mittelalter/models/block/arrow_slit.json"
  "assets/mittelalter/textures/block/arrow_slit.png"
  "assets/mittelalter/textures/item/longsword.png"
  "data/mittelalter/recipe/longsword.json"
  "data/mittelalter/recipe/command_baton.json"
  "data/mittelalter/recipe/tournament_standard.json"
  "data/mittelalter/recipe/camelot_charter.json"
  "data/mittelalter/loot_table/blocks/arrow_slit.json"
  "data/mittelalter/neoforge/biome_modifier/add_silver_ore.json"
  "data/minecraft/tags/block/mineable/pickaxe.json"
)

missing=0
for entry in "${required_entries[@]}"; do
  if ! grep -Fqx "$entry" "$entry_file"; then
    echo "MISSING $entry" >&2
    missing=1
  fi
done

forbidden_regex='(^|/)(src|test|tests|workflow|openspec)/|^de/mittelalter/gametest/|(^|/)textures/source/|(^|/)\.cache/|(^|/)\.DS_Store$|\.(bbmodel|java|kt|kts|psd|xcf)$'
forbidden_entries="$(grep -E "$forbidden_regex" "$entry_file" || true)"
if [[ -n "$forbidden_entries" ]]; then
  echo "release-artifact-audit: development-only entries found:" >&2
  printf '%s\n' "$forbidden_entries" >&2
  exit 1
fi

if [[ $missing -ne 0 ]]; then
  echo "release-artifact-audit: required release entries are missing" >&2
  exit 1
fi

if ! unzip -p "$artifact" META-INF/neoforge.mods.toml | grep -Fq 'version="0.1.0-rc.1"'; then
  echo "release-artifact-audit: metadata does not declare version 0.1.0-rc.1" >&2
  exit 1
fi

if stat -f '%z' "$artifact" >/dev/null 2>&1; then
  byte_size="$(stat -f '%z' "$artifact")"
else
  byte_size="$(stat -c '%s' "$artifact")"
fi

if command -v sha256sum >/dev/null 2>&1; then
  checksum="$(sha256sum "$artifact" | awk '{print $1}')"
else
  checksum="$(shasum -a 256 "$artifact" | awk '{print $1}')"
fi

echo "release-artifact-audit: PASS"
echo "artifact=$(basename "$artifact")"
echo "bytes=$byte_size"
echo "sha256=$checksum"
echo "required_entries=${#required_entries[@]}"
