# Contributing

Thanks for considering a contribution to `spring-chain-of-responsibility`.

## Getting started

Requires Java 17+ and Maven. Main sources are Java (`src/main/java`); tests are Kotlin
(`src/test/kotlin`), using JUnit 5, Kotest assertions, and MockK.

```bash
mvn clean test              # run all tests
mvn test -Dtest=ChainFactoryTest   # run a single test class
mvn spotless:check          # verify formatting
mvn spotless:apply          # auto-fix formatting
```

CI runs `spotless:check`, `clean test`, and `package -DskipTests` on Java 17 and 21 — make sure
these pass locally before opening a PR.

## Code style

Formatting is enforced by Spotless (`google-java-format` for Java, `ktlint` for Kotlin) and
checked in CI. Run `mvn spotless:apply` before committing if `spotless:check` fails.

## Commit messages and PR titles

Commit messages and PR titles must start with one of the following prefixes — this is what
`.github/cliff.toml` uses to build the changelog on release, and anything else is silently
dropped from it:

| Prefix       | Changelog section  |
|--------------|---------------------|
| `feature:`   | 🚀 Features         |
| `fix:`       | 🐛 Bug Fixes         |
| `docs:`      | 📚 Documentation     |
| `refactor:`  | ♻️ Refactoring       |
| `chore:`     | 🔧 Misc              |

Since PRs are squash-merged, the **PR title** is what lands on `main` and gets parsed — get the
prefix right there.

## Submitting changes

1. Fork the repository and create a branch off `main`.
2. Make your change, with tests where it makes sense.
3. Open a pull request against `main` with a title following the convention above.
4. Make sure CI is green.

For anything beyond a small fix, consider opening an issue first to discuss the approach.
