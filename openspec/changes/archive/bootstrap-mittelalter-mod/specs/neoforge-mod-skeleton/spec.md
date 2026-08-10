## ADDED Requirements

### Requirement: Buildable NeoForge Gradle project
The repository SHALL contain a NeoForge Gradle mod project (`build.gradle`, `settings.gradle`, `gradle.properties`, Gradle wrapper) that builds successfully with `./gradlew build` using a JDK 25 toolchain.

#### Scenario: Clean build succeeds
- **WHEN** a contributor with JDK 25 installed runs `./gradlew build` in a freshly cloned checkout
- **THEN** the build completes successfully and produces a mod jar under `build/libs/`

#### Scenario: Version metadata is centralized
- **WHEN** a contributor needs to know the target Minecraft/NeoForge version
- **THEN** they can find it in `gradle.properties` (`minecraft_version`, `neo_version`) without inspecting build logic

### Requirement: Registered placeholder content proves the wiring
The mod skeleton SHALL register at least one placeholder block, one placeholder item, and one creative mode tab through NeoForge's `DeferredRegister` mechanism, wired to the mod's event bus.

#### Scenario: Placeholder block appears in-game
- **WHEN** the mod is loaded in a client instance (`./gradlew runClient`)
- **THEN** the placeholder block and item are registered under the `mittelalter` namespace and appear in the mod's own creative mode tab

### Requirement: Mod metadata uses templated values
The mod's `neoforge.mods.toml` SHALL be generated from `src/main/templates` using the `${mod_id}`, `${mod_name}`, `${mod_version}`, `${mod_license}`, `${neo_version}`, and `${minecraft_version_range}` placeholders defined in `gradle.properties`, so metadata never needs to be edited in two places.

#### Scenario: Changing the mod version
- **WHEN** a contributor bumps `mod_version` in `gradle.properties`
- **THEN** the built mod jar's `neoforge.mods.toml` reflects the new version without any other file changes

### Requirement: CI verifies the build
A GitHub Actions workflow SHALL run `./gradlew build` on a JDK 25 toolchain for every push and pull request.

#### Scenario: Pull request with a broken build
- **WHEN** a pull request introduces a Gradle or compilation error
- **THEN** the `gradle-build` CI check fails and blocks merge
