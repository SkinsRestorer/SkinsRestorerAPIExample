# Contribute to SkinsRestorerAPIExample

Contributions can fix behavior, improve documentation, or add focused tests.
Read the [API guide](https://skinsrestorer.net/docs/development/api) and [Javadocs](https://jd.skinsrestorer.net).

## Before you start

Read [the support guide](SUPPORT.md) for questions and issue routing.
Search existing issues and pull requests. Discuss larger API, architecture, or dependency changes before implementation.

Work from `main` and target that branch in your pull request.
Keep each change focused. Avoid unrelated formatting and dependency updates.

## Prepare a checkout

Install JDK 21 and Maven. The example compiler settings and API dependency are in `pom.xml`.

Run the commands below from the repository root unless a command names another directory.
On Windows, use `gradlew.bat` in place of `./gradlew` for Gradle commands.

## Repository layout

- `src/main/`: example plugin source and metadata.
- `pom.xml`: API dependency and build settings.

## Verify your change

```bash
mvn -B verify
```

Keep the example small enough for a new plugin author to follow. Use the public SkinsRestorer API. Explain version-dependent behavior. Verify the built plugin on a disposable server with the matching SkinsRestorer release. Maven alone does not prove the runtime integration.

Run the relevant checks before review. State the command and result in the pull request.
If a check cannot run, explain the missing dependency or service. Do not claim it passed.
Keep generated artifacts consistent with their source and review their diff.

## Style and documentation

Follow the existing code conventions and repository formatter. Keep commit hooks enabled.
Add focused tests for changed logic when practical. Avoid tests that only assert source strings.
Update documentation when commands, APIs, configuration, or expected behavior change.
Keep examples small and reproducible. Preserve exact identifiers, commands, and error messages.

## Open a pull request

Explain the problem and resulting behavior. Link related issues without a placeholder issue number.
Include the Maven result and runtime verification steps.
Include commands and results. State any runtime checks that remain necessary.
Respond to review with a correction or concrete evidence.

Use Conventional Commits: `type(scope): description`, for example `docs(contributing): explain local validation`.
Use a meaningful scope, or omit it. Keep the subject concise and imperative.
Add a body when the reason or compatibility impact is not obvious.

For vulnerabilities, follow [the security reporting instructions](SECURITY.md).
Remove credentials and private data from examples, logs, and screenshots.
