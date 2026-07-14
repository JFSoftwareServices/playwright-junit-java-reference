# Generating and Viewing Allure Reports (Windows & macOS)

## Overview

This document explains how to:

1. Install the Allure Commandline.
2. Generate Allure test results.
3. Create an Allure HTML report.
4. Start a local web server to view the report.

Opening `index.html` directly using `file://` is **not recommended** because Allure loads report data dynamically and modern browsers may block those requests.

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

```powershell
mvn test
```

---

# Step 1 - Run the Tests

Execute the test suite.

Windows:

```powershell
mvn test
```

macOS:

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

macOS:

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

macOS:

```bash
cd allure-report
```

Start the Java built-in web server.

Windows:

```powershell
jwebserver
```

macOS:

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

# Complete macOS Workflow

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

macOS:

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

macOS:

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

```powershell
mvn test
```

Generate report:

```powershell
allure generate allure-results -o allure-report --clean
```

Serve report automatically:

```powershell
allure serve allure-results
```

Start the Java web server:

```powershell
cd allure-report

jwebserver
```

Verify Allure installation:

```powershell
allure --version
```

Verify Java installation:

```powershell
java -version
```

Verify Maven installation:

```powershell
mvn -version
```

