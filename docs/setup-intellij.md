# IntelliJ Setup


## Prerequisites

Install:

- Java 21
- Maven
- IntelliJ IDEA
- Git


## Import Project


Open IntelliJ:

File
→ Open
→ Select pom.xml


Reload Maven dependencies.


## Verify Installation


Run:

mvn clean test


## Install Playwright Browsers


Run:

mvn exec:java \
-Dexec.mainClass=com.microsoft.playwright.CLI \
-Dexec.args="install"