<!-- AMENDED: retitled from "(Windows & macOS)" to reflect added Linux
     coverage and Docker guidance below. -->
# Generating and Viewing Allure Reports (Windows, macOS & Linux)

## Overview

This document explains how to:

1. Install the Allure Commandline.
2. Generate Allure test results.
3. Create an Allure HTML report.
4. Start a local web server to view the report.
5. View a captured Playwright trace.

Opening `index.html` directly using `file://` is **not recommended** because Allure loads report data dynamically and modern browsers may block those requests.

For what gets *recorded* into a test run (annotations, `@Step` reporting,
failure attachments, network logs), see [Allure Reporting](allure-reporting.md).
This document only covers installing the tooling and generating/viewing the
report from results that already exist.

---

# Installing Allure Commandline

## Windows (Recommended)

Open **PowerShell as Administrator**.

To do this:

1. Press **Start**
2. Search for **PowerShell**
3. Right-click **Windows PowerShell** (or **PowerShell**)
4. Select **Run as administrator**

### Install using Windows Package Manager (winget)

```powershell
winget install Qameta.Allure
```

To upgrade an existing installation:

```powershell
winget upgrade Qameta.Allure
```

Restart PowerShell after installation.

Verify the installation:

```powershell
allure --version
```

Expected output:

```
2.44.0
```

---

## Windows (Manual Installation from GitHub)

If you prefer not to use `winget`, you can install Allure manually.

1. Download the latest Allure Commandline release from GitHub:

https://github.com/allure-framework/allure2/releases

2. Download the ZIP archive (for example):

```
allure-2.44.0.zip
```

3. Extract it to a permanent location, for example:

```
C:\Tools\allure
```

The directory should contain:

```
C:\Tools\allure
    ├── bin
    ├── config
    ├── lib
```

4. Add the **bin** directory to your Windows PATH.

For example:

```
C:\Tools\allure\bin
```

5. Close and reopen PowerShell.

Verify the installation:

```powershell
allure --version
```

---

## macOS

Install Homebrew if it is not already installed.

Install Allure:

```bash
brew install allure
```

Upgrade an existing installation:

```bash
brew upgrade allure
```

Verify:

```bash
allure --version
```

Expected output:

```
2.44.0
```

---

## Linux

Allure Commandline does not have a native `apt`/`yum` package, so install it
manually from the GitHub release, the same way as the Windows manual method:

1. Download the latest release archive:

https://github.com/allure-framework/allure2/releases

2. Download the `.tgz` archive (for example):

```
allure-2.44.0.tgz
```

3. Extract it to a permanent location:

```bash
sudo tar -xzf allure-2.44.0.tgz -C /opt/
sudo mv /opt/allure-2.44.0 /opt/allure
```

4. Add the **bin** directory to your `PATH`. Add this line to `~/.bashrc`,
   `~/.zshrc`, or the shell profile used by your environment (including
   inside a Codespaces/devcontainer image):

```bash
export PATH="$PATH:/opt/allure/bin"
```

5. Reload the shell profile and verify:

```bash
source ~/.bashrc   # or ~/.zshrc
allure --version
```

Expected output:

```
2.44.0
```

---

# Prerequisites

Ensure the following are installed:

- Java JDK 21 or later
- Maven
- Allure Commandline
- A test framework configured to generate Allure results (JUnit 5, TestNG, etc.)

Verify each installation:

```bash
java -version
```

```bash
mvn -version
```

```bash
allure --version
```

---

# Project Structure Example

After running the test suite, your project should contain:

```
playwright-junit-java-reference
│
├── allure-results
│   ├── <test>-result.json
│   ├── <test>-container.json
│   └── ...
│
├── allure-report
│
└── pom.xml
```

The `allure-results` directory is created automatically when the test framework executes.

Example:

```bash
mvn test
```

---

# Step 1 - Run the Tests

Execute the test suite.

Windows:

```powershell
mvn test
```

macOS / Linux:

```bash
mvn test
```

The framework should generate:

```
allure-results
```

containing files similar to:

```
*-result.json
*-container.json
```

---

# Step 2 - Generate the HTML Report

From the project root:

Windows:

```powershell
allure generate allure-results -o allure-report --clean
```

macOS / Linux:

```bash
allure generate allure-results -o allure-report --clean
```

Explanation:

| Command | Description |
|----------|-------------|
| `allure generate` | Generates an HTML report |
| `allure-results` | Input directory containing Allure result files |
| `-o allure-report` | Output directory for the generated report |
| `--clean` | Removes any previous report before generating a new one |

Expected output:

```
Report successfully generated to allure-report
```

---

# Step 3 - Start a Local Web Server

Navigate into the generated report directory.

Windows:

```powershell
cd allure-report
```

macOS / Linux:

```bash
cd allure-report
```

Start the Java built-in web server.

Windows:

```powershell
jwebserver
```

macOS / Linux:

```bash
jwebserver
```

Expected output:

```
Serving http://localhost:8000/
```

---

# Step 4 - View the Report

Open your browser and navigate to:

```
http://localhost:8000
```

The Allure report should load successfully.

---

# Complete Windows Workflow

```powershell
cd C:\Projects\playwright-junit-java-reference

mvn test

allure generate allure-results -o allure-report --clean

cd allure-report

jwebserver
```

Open:

```
http://localhost:8000
```

---

# Complete macOS / Linux Workflow

```bash
cd ~/Projects/playwright-junit-java-reference

mvn test

allure generate allure-results -o allure-report --clean

cd allure-report

jwebserver
```

Open:

```
http://localhost:8000
```

---

# Alternative - Built-in Allure Server

Instead of generating the report manually, Allure can generate and serve the report automatically.

Windows:

```powershell
allure serve allure-results
```

macOS / Linux:

```bash
allure serve allure-results
```

This command:

- Generates the report
- Starts a temporary web server
- Opens the report automatically in your default browser

This is useful for local development.

For CI/CD environments, using `allure generate` followed by `jwebserver` more closely mirrors how reports are generated and published.

---

# Generating Reports from Docker-Executed Test Runs

When tests run inside a container, see [Docker Execution](docker-execution.md)
for the full setup. In summary: `allure-results` is written inside the
container's filesystem unless a volume is mounted, so the project's Docker
setup mounts it (alongside `test-results`) explicitly:

```bash
docker run \
-v ./test-results:/app/test-results \
-v ./allure-results:/app/allure-results \
playwright-java-framework
```

Once the container exits, `allure-results` will exist on the host at the
usual path. Run `allure generate allure-results -o allure-report --clean`
and `jwebserver` on the host exactly as in Steps 2–4 above.

If the volumes are not mounted, the results only exist inside the (now
stopped) container and must be copied out with `docker cp` before they can
be generated into a report.

---

# Viewing Playwright Traces

Playwright traces captured on failure (see [Allure Reporting](allure-reporting.md#playwright-trace-capture))
can be viewed using the Playwright Trace Viewer.

If Node.js is installed, open a trace using:

```bash
npx playwright show-trace test-results/traces/<trace-file>.zip
```

The Trace Viewer provides an interactive timeline of the test execution, allowing inspection of every recorded browser action.

---

# Troubleshooting

## Blank Page

Do **not** open:

```
file:///.../allure-report/index.html
```

Instead, start a local web server and open:

```
http://localhost:8000
```

---

## "Data Not Found" or HTTP 500 Errors

This usually occurs when `index.html` is opened directly from the file system.

Always use:

```
jwebserver
```

or

```
allure serve
```

---

## Verify Test Results Exist

Windows:

```powershell
dir allure-results
```

macOS / Linux:

```bash
ls allure-results
```

You should see files similar to:

```
xxxxxxxx-result.json
xxxxxxxx-container.json
```

If these files do not exist, the tests have not generated any Allure results.

---

## Verify the Report Was Generated

Check that the following directory exists:

```
allure-report
```

Expected contents include:

```
index.html

data/

plugins/

history/
```

---

## Regenerate the Report

If the report appears incomplete:

```powershell
allure generate allure-results -o allure-report --clean
```

Then restart the web server:

```powershell
cd allure-report

jwebserver
```

---

# Useful Commands

Run tests:

```bash
mvn test
```

Generate report:

```bash
allure generate allure-results -o allure-report --clean
```

Serve report automatically:

```bash
allure serve allure-results
```

Start the Java web server:

```bash
cd allure-report

jwebserver
```

Verify Allure installation:

```bash
allure --version
```

Verify Java installation:

```bash
java -version
```

Verify Maven installation:

```bash
mvn -version
```
