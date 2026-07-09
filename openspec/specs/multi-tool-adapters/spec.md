# Multi-Tool Adapters

## Requirements

### Requirement: Supported tools expose the same canonical workflow
The repository SHALL support Codex, Claude Code, and Gemini CLI through adapter files that map into the same canonical workflow.

#### Scenario: Tool-specific command is invoked
- **WHEN** a user starts a planning, verification, archive, status, or session-close flow from a supported tool
- **THEN** the adapter SHALL route the user toward `AGENTS.md`, `openspec/`, and `workflow/scripts/`

### Requirement: Adapters are accelerators
Tool-native features MAY accelerate work but SHALL NOT become the sole source of workflow truth.

#### Scenario: Claude subagent is used
- **WHEN** Claude Code delegates implementation or review to a subagent
- **THEN** that subagent SHALL still read canonical artifacts
- **AND** report back into canonical artifacts or summaries

#### Scenario: Gemini subagent is used
- **WHEN** Gemini CLI delegates implementation, review, verification, or handover work to a subagent
- **THEN** that subagent SHALL still read canonical artifacts
- **AND** report back into canonical artifacts or summaries

### Requirement: Gemini adapter supports isolated delegation
The repository SHALL allow Gemini CLI to use project-level subagents for bounded work without making those subagents canonical.

#### Scenario: Gemini handles a larger implementation slice
- **WHEN** the main Gemini agent would otherwise accumulate unnecessary implementation detail
- **THEN** the adapter SHALL make project-level Gemini subagents available
- **AND** the main agent MAY delegate automatically or explicitly with `@agent`
- **AND** the subagent SHALL remain an accelerator over the canonical workflow
