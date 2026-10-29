# Distributed Java UI & API Test Automation Framework

An enterprise-grade, thread-safe test automation architecture built with **Java 17**, **Selenium WebDriver 4**, **REST Assured**, **TestNG**, **Docker Selenium Grid**, and **GitHub Actions CI/CD**.

## Key Engineering Highlights
- **Thread-Safe Parallel Execution:** Uses Java `ThreadLocal<WebDriver>` in `DriverFactory` to prevent session collisions during high-concurrency parallel runs (`thread-count="4"`).
- **Flaky Test Mitigation:** Implements custom TestNG `IRetryAnalyzer` to isolate transient network/environment glitches before failing CI pipelines.
- **Containerized Grid Support:** Includes `docker-compose.yml` to spin up an isolated Selenium 4 Hub and Chrome nodes for hermetic execution.
- **CI/CD Quality Gate:** Configured with GitHub Actions (`regression.yml`) to trigger automated pre-merge test runs on every pull request and push.

## Quick Start
```bash
# Run locally in headless mode
mvn clean test

# Run against Dockerized Selenium Grid
docker compose up -d
mvn clean test -Dremote=true -DgridUrl=http://localhost:4444/wd/hub
docker compose down