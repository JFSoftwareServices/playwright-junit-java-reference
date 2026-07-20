# Dependency Management

## JUnit Platform and Allure Version Alignment

This framework uses:

- JUnit Jupiter: 5.13.4
- JUnit Platform: 1.13.4
- Allure JUnit 5: 2.29.1
- Maven Surefire: 3.5.2

### Background

JUnit 5 consists of multiple components that work together during test execution:

```text
JUnit Jupiter
    |
    +-- junit-jupiter-api
    |
    +-- junit-jupiter-engine
              |
              v
       junit-platform-engine
              ^
              |
    junit-platform-launcher
              |
              v
    Test discovery and execution
```

The JUnit Platform components must use compatible versions.

| Component | Purpose |
|---|---|
| junit-jupiter-api | Provides JUnit 5 annotations and APIs |
| junit-jupiter-engine | Executes JUnit 5 tests |
| junit-platform-engine | Provides the JUnit execution engine |
| junit-platform-launcher | Discovers and launches tests |
| Maven Surefire | Executes tests during Maven builds |
| Allure JUnit 5 | Integrates tests with Allure reporting |

---

### Problem Encountered

After adding Allure reporting support, Maven resolved conflicting JUnit Platform dependencies.

The dependency tree showed:

```text
junit-platform-engine       1.13.4
junit-platform-launcher     1.10.3
```

The older `junit-platform-launcher` version was introduced as a transitive dependency.

This created a mismatch between:

- The JUnit Platform engine used by JUnit Jupiter
- The JUnit Platform launcher responsible for test discovery

---

### How the Issue Was Reported

The failure occurred during the JUnit 5 test discovery phase.

The execution flow was:

```text
Maven
  |
  v
Maven Surefire Plugin
  |
  v
JUnit Platform Launcher
  |
  v
JUnit Jupiter Engine
  |
  v
Test Discovery Failed
```

The exception reported was:

```text
org.junit.platform.commons.JUnitException:

OutputDirectoryProvider not available;
probably due to unaligned versions of the
junit-platform-engine and junit-platform-launcher
jars on the classpath/module path.
```

The responsibility chain was:

- JUnit Platform detected the incompatible versions
- JUnit Platform Commons created the exception
- JUnit Jupiter Engine failed during test discovery
- Maven Surefire surfaced the failure in the Maven build output

---

### Diagnosing the Problem

The dependency tree was inspected using:

```bash
mvn dependency:tree -Dverbose | grep junit-platform
```

The conflicting versions were identified:

```text
junit-platform-engine:1.13.4

junit-platform-launcher:1.10.3
```

This confirmed that JUnit Platform components were not aligned.

---

### Resolution

The dependency mismatch was resolved by explicitly defining the JUnit Platform launcher version in `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-launcher</artifactId>
    <version>1.13.4</version>
    <scope>test</scope>
</dependency>
```

This forces Maven to use aligned JUnit Platform versions:

```text
junit-platform-engine       1.13.4
junit-platform-launcher     1.13.4
```

instead of mixing incompatible versions.

---

### Verification

The dependency tree was checked again:

```bash
mvn dependency:tree -Dverbose | grep junit-platform
```

Expected output:

```text
junit-platform-commons:1.13.4
junit-platform-engine:1.13.4
junit-platform-launcher:1.13.4
```

The test suite then executed successfully:

```bash
mvn clean test
```

---

### Key Learning

When adding frameworks such as Allure, always check for transitive dependency conflicts.

A stable JUnit 5 automation framework requires compatible versions across:

```text
JUnit Jupiter
        |
        v
JUnit Platform Engine
        |
        v
JUnit Platform Launcher
        |
        v
Maven Surefire
```

Keeping these dependencies aligned ensures:

- Reliable JUnit 5 test discovery
- Stable Maven test execution
- Successful Allure report generation
