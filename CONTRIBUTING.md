# Contributing

When contributing to this repository, please first discuss the change you wish to make in a new issue before making a change.

Please note we have a [code of conduct](CODE_OF_CONDUCT.md), please follow it in all your interactions with the project.

## Development guidelines

Principles for working on Cluecumber — for contributors and for AI-assisted development.

### Core principles

#### YAGNI (You Aren't Gonna Need It)

- Implement what is needed now, not what might be needed later.
- Do not add configuration, abstractions, or extension points “just in case”.
- Do not add tests, docs, or refactors beyond the scope of the task unless they directly support it.

#### Prefer native dependency behavior

Use what libraries already provide before writing custom logic.

| Area | Prefer | Avoid |
|------|--------|--------|
| Charts (Chart.js) | `responsive`, `aspectRatio`, built-in resize | Manual canvas sizing, custom `ResizeObserver` loops, post-create option mutation |
| Templates (Pebble) | `{% include %}`, standard filters/tests | Reimplementing Freemarker patterns that Pebble does not support |
| Layout (Bootstrap) | Grid, utilities, responsive breakpoints | Parallel custom layout systems |
| Tables | DataTables responsive mode | Custom table reflow logic |

**Rule of thumb:** if a dependency documents a way to do something, try that first. Custom code is justified only when native behavior is insufficient or buggy — and then keep the workaround as small as possible.

#### Minimize scope

- The smallest correct diff is the best diff.
- Do not touch unrelated files, refactors, or “drive-by” cleanups.
- One logical change per commit/PR when possible.

#### Match existing conventions

- Read surrounding code before adding new code.
- Reuse existing builders, POJOs, template macros/includes, and naming patterns.
- New code should look like it was written by the same author as the file it lives in.

#### Avoid over-engineering

- Do not introduce helpers, base classes, or indirection for one or two call sites.
- Do not add excessive error handling for impossible or extremely unlikely cases.
- Prefer inline logic when it is clearer than a named abstraction.

#### Comments

- Code should mostly explain itself.
- Comment only non-obvious business rules, compatibility workarounds, or constraints that are not visible from the code (e.g. “Chart.js 4 breaks if options are mutated after `new Chart()`”).

#### Tests

- Add or update tests when they protect real behavior or guard against regressions.
- Do not add tests that merely restate the implementation or assert obvious getters/setters.

### Cluecumber-specific notes

#### Pebble templates

- **Macros do not inherit template context.** Use `{% include %}` when a partial needs `navigationLinks`, `reportDetails`, `_model`, etc.
- **Do not use `.peb` in `{% import %}` or `{% include %}` paths** — the loader adds the suffix; `page.peb.peb` will result otherwise.
- Use Pebble syntax, not Freemarker idioms:
  - `{{ x ? 'a' : 'b' }}` not `{{ 'a' if x else 'b' }}`
  - `{% for entry in map %}` with `entry.key` / `entry.value`
  - `not condition` not `!condition`

Keep **macros** for self-contained fragments that only use passed parameters (e.g. `cardStart`, `cardEnd`).

#### Chart rendering

- Let Chart.js manage canvas size and resize when `responsive: true` is appropriate.
- Configure click handlers and plugins on the **config object before** `new Chart(...)`. Do not mutate `chart.options` after creation if the chart may be destroyed and recreated.
- Give chart containers explicit dimensions in CSS (`position: relative`, fixed height). Do not force `width`/`height: 100%` on the canvas if that conflicts with Chart.js sizing.
- After chart changes, regenerate the example report and check both pie and bar charts at desktop and narrow viewport widths.

#### Java / Maven

- Prefer centralized dependency versions in the parent `pom.xml` `dependencyManagement`.
- Follow existing package structure under `engine`, `core`, and `maven`.
- Do not upgrade major dependencies casually; verify template/API compatibility (e.g. Chart.js 2 → 4, Freemarker → Pebble).

### Workflow expectations

1. **Understand the task** — fix the reported problem, not a larger rewrite.
2. **Implement the minimal fix** — native behavior first, custom code second.
3. **Verify** — run relevant tests (`mvn -pl engine test` for engine/template changes) and regenerate example reports when UI output changes.
4. **Document only when useful** — update README/CHANGELOG when behavior or public API changes; do not add docs for internal refactors unless asked.

### Checklist before opening a PR

- [ ] Change is scoped to the task; no unrelated edits
- [ ] Dependency-native approach considered before custom logic
- [ ] Existing patterns and naming followed
- [ ] Templates use includes where context is required
- [ ] Tests run and pass for affected modules
- [ ] Visual/report output checked when templates, CSS, or JS changed

## Pull Request Process

Update the README.md with details of changes to the interface, this includes new system properties and useful file locations.
Increase the version numbers in any examples files and the README.md to the new version that this Pull Request would represent. The versioning scheme we use is SemVer.
You may merge the Pull Request in once you have the sign-off of the repository owner(s), or if you do not have permission to do that, you may request the reviewer to merge it for you.
