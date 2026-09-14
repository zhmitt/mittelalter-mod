# Authority procedure

Read only when the core names this trigger. Retained repository-specific details.

## Proactive orchestration v3

Scheduling is based on active mutable ownership, not unfinished-change count.
Use one state per change: `active_write`, `external_wait`,
`user_action_pending`, `parked`, `ready_for_verify`,
`ready_for_archive`, or `done`. Only `active_write` consumes a writing
lane. Where older text limits "unverified claims", this section supersedes
that scheduling rule; the repository's canonical completion gate still remains
the only definition of Done.

Release a writing lane only through a Claim Release checkpoint recording the
baseline and preservation reference, completed work, open gates/risks, exact
resume step, released files/resources, absence of writing workers, and
Integration Owner confirmation. Claim Release never implies completion.

Within the accepted outcome and ownership, execute safe routine actions without
a conversational micro-approval: read-only status/auth checks, required
interactive sign-in initiation, local tests/builds, scoped worktree/worker setup,
focused commits, evidence synchronization, small integration fixes, and
external-wait monitor creation. Starting sign-in does not authorize credentials,
MFA, new consent, or ambiguous account/tenant choices.

Still require explicit authorization for production mutation/deployment,
IAM/secret/permission changes, new or unbounded paid work, public communication,
irreversible deletion, material product decisions, and push/merge unless already
granted for the session. Native sandbox, connector, and approval boundaries and
stricter repository-specific rules always prevail.

The Main/Integration Owner may perform small conflict resolution,
state/evidence synchronization, focused governance and integration patches, Git
operations, and gates directly when delegation would add more coordination than
risk reduction. Independent product slices and broad writes remain worker work.
Verification is delta- and risk-based: reuse valid evidence and rerun what
changed; reserve Full Evidence for high-risk or difficult-to-reverse work.

Before stopping because progress cannot continue, execute any already-authorized
recovery action. Otherwise return:

```text
Recovery Contract
State: <external_wait|user_action_pending|parked>
Blocker: <exact blocker>
Preserved: <commit/stash/artifact and completed work>
Recommendation: <default next path>
Alternatives: <zero to two bounded alternatives>
Authorized next action: <action executable without another decision, or none>
Resume trigger: <observable event/owner>
Next command or action: <exact continuation>
```

A global Codex Stop hook may request one continuation pass when this contract is
missing; `stop_hook_active` prevents loops. The hook is a recovery failsafe,
not a scheduler or permission bypass.

## Approval-churn prevention

Before dispatch, the Main/Integration Owner performs a **capability preflight**:
resolve the worker's required filesystem, process, network, connector, remote
runtime and external-write capabilities; choose a runtime that already supports
the authorized work; pre-position exact commits, mirrors or non-secret artifacts
when transfer would otherwise cause repeated elevation; and record an
**approval budget**. The default budget is zero conversational approvals and at
most one consolidated native platform approval for a known, bounded capability
gap.

A worker must not discover predictable permissions one command at a time. At
startup it validates the authority contract and required capabilities
read-only. If a required capability is absent, it reports one consolidated
mismatch containing the blocked capability, resolved target/effect, why the
current runtime cannot proceed, the narrowest safe grant or suitable runtime,
and preserved state. It must not issue serial approval prompts or try command
variants that cross the same boundary.

Prefer designs that eliminate privileged observation: repository-owned
instrumentation, process-local lifecycle evidence, deterministic harnesses, and
pre-provisioned verification runtimes are preferred over OS-wide process
inspection or ad-hoc observer scripts. After one native permission mismatch or
one approval-gated method proves operationally unsuitable, park that method;
resume only with an already-authorized capability, a redesigned bounded method,
or a newly provisioned runtime. Approval denial never authorizes retries,
fallback mutation, broader inspection, or scope expansion.

When the user or parent explicitly authorizes uninterrupted execution until a
real blocker, workers treat every in-scope action inside the effective authority
intersection and approval budget as authorized. They interrupt only for an
explicit user gate, an unavailable required native capability after the
consolidated preflight, or a material ambiguity in target or effect.

## External wait continuity

When the primary orchestrator cannot make useful progress because an already
running external operation is pending (for example CI, a remote test, build,
deploy, import, provider job, or approval), it must establish continuation
before yielding:

- record the exact operation identifier, authoritative read-only status check,
  owner, polling cadence/backoff, timeout, terminal success and failure states,
  and the next action for each terminal state;
- create exactly one thread-bound wake-up monitor with the runtime's supported
  automation or recurring follow-up mechanism; while pending it stays quiet, on
  a terminal result it wakes the owning session, reports evidence, continues the
  recorded next action when authorized, and then disables itself;
- reuse an existing monitor for the same operation instead of creating duplicate
  polling, CI runs, deployments, or notifications;
- never restart, cancel, replace, deploy, merge, or otherwise mutate the external
  operation merely to make monitoring easier unless that mutation is separately
  authorized;
- cancel or disable the monitor when the operation becomes terminal, obsolete,
  superseded, manually stopped, or the owning task closes.

A repository polling script alone is not a wake-up guarantee. If the active
runtime cannot create a durable wake-up monitor, state that limitation, preserve
the continuation contract in the handoff, and use bounded foreground polling
only when practical. Never claim autonomous continuation unless the wake-up
path was actually created and its ownership is known.

## Environment-scoped autonomy

For a bounded Sandbox, Dev or Staging outcome, the Integration Owner MAY record
one **environment-scoped authorization envelope** instead of asking for
conversational re-approval at each derived step. The envelope names the
authoritative target identity (provider project, account or tenant, and actual
environment), the allowed operation chain, candidate-resolution rule, test-data
bound, invariants, exclusions, expiry or stop condition, and any genuine
human-only input. A branch name, provider UI label, or source ref alone is not
the environment identity.

Inside a valid envelope and the effective inherited authority, dependent steps
such as a required canonical push, CI wait, bounded non-production deploy,
configuration repair, and smoke test proceed without conversational
micro-approval. An envelope never authorizes Production, IAM/secret or
permission changes, real payments, public communication, irreversible work,
new or unbounded cost, material scope expansion, or a target outside the named
environment. It also cannot expand a native sandbox, connector, or provider
permission boundary.

Use `user_action_pending` only for a genuine human input or judgment: for
example MFA, an inbox-only code, choosing an ambiguous account or tenant,
professional risk acceptance, or an explicit material decision. A known
Staging prerequisite, derived commit identity, expected CI wait, or permitted
test record is not a user-action gate. If the runtime rejects an otherwise
enveloped external write, issue at most one consolidated native-capability
mismatch; do not convert it into serial conversational confirmation requests.

An `external_wait` monitor remains terminally quiet: it may record and poll
authoritative status without periodic narrative updates, and wakes its owner
only for terminal success, terminal failure, timeout, or a required genuine
human input. A scope/over-engineering audit is read-only: it classifies active
work against the Outcome Anchor as `keep`, `park`, `split`, `close`, or
`needs_decision`; it does not create tasks, alter workflow state, or authorize
implementation by itself.
