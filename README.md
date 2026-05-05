# QA Assist Generator

An offline, local Java tool that generates structured QA test cases from feature details using rule-based templates. No internet required. No AI or external API calls. Pure Java logic.

---

## What It Does

You fill in a form with details about a feature — name, type, priority, and which test types you want. The tool generates a set of relevant test cases instantly, ready to view on screen, download as CSV, or download as Excel.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x (Phase 1 wrap — engine is plain Java) |
| UI | Thymeleaf (server-side HTML, single JAR) |
| CSV export | Built-in Java |
| Excel export | Apache POI |
| JSON export | Jackson |
| Build | Maven |

---

## Input Form

| Field | Type | Options |
|---|---|---|
| Feature Name | Text | Free text |
| Feature Type | Dropdown | UPLOAD, LOGIN, SEARCH, REGISTER, API, FORM |
| Description | Textarea | Optional |
| Priority | Dropdown | HIGH, MEDIUM, LOW |
| Test Types | Checkboxes | HAPPY_PATH, NEGATIVE, BOUNDARY, EDGE |

---

## Supported Feature Types

| Feature Type | Test Cases Generated |
|---|---|
| UPLOAD | Valid file, unsupported type, missing file, max size, exceeds max size, empty file |
| LOGIN | Valid credentials, wrong password, empty fields, unregistered email, max length password, locked account |
| SEARCH | Valid keyword, no results, empty query, max length query, special characters, SQL injection, script injection |
| REGISTER | Valid data, duplicate email, weak password, missing fields, invalid email format, max length username, special characters, SQL injection, script injection |
| API | 200 OK, 400 missing field, 401 no auth, 403 forbidden, 404 not found, large payload, malformed JSON, SQL injection, script injection, expired token, IDOR, mass assignment |
| FORM | Valid submit, required fields empty, invalid email, max length input, special characters, rapid double submit, SQL injection, script injection |

---

## Project Structure

```
src/main/java/com/qaassist/generator/
├── engine/
│   ├── model/
│   │   ├── FeatureType.java         — enum: UPLOAD, LOGIN, SEARCH, REGISTER, API, FORM
│   │   ├── TestType.java            — enum: HAPPY_PATH, NEGATIVE, BOUNDARY, EDGE
│   │   ├── Priority.java            — enum: HIGH, MEDIUM, LOW
│   │   ├── TestCase.java            — single test case (id, title, precondition, steps, expectedResult, testType, priority)
│   │   └── TestCaseRequest.java     — form input (featureName, featureType, description, priority, testTypes)
│   ├── template/
│   │   ├── TestCaseTemplate.java    — interface: generate(TestCaseRequest) → List<TestCase>
│   │   ├── UploadTemplate.java
│   │   ├── LoginTemplate.java
│   │   ├── SearchTemplate.java
│   │   ├── RegisterTemplate.java
│   │   ├── ApiTemplate.java
│   │   └── FormTemplate.java
│   ├── registry/
│   │   └── TemplateRegistry.java    — EnumMap wiring FeatureType → Template
│   └── TestCaseGeneratorService.java
├── web/
│   └── TestCaseController.java      — GET / (form), POST /generate (results), POST /download-csv, POST /download-excel
└── QaAssistGeneratorApplication.java — Spring Boot entry point

src/main/resources/templates/
├── index.html                        — Thymeleaf input form
└── results.html                      — Thymeleaf results table
```

---

## Design

The engine uses the **Strategy pattern** — each `FeatureType` has its own template class. Adding a new feature type means creating one new class. Nothing else changes.

```
TestCaseGeneratorService
    └── TemplateRegistry (EnumMap<FeatureType, TestCaseTemplate>)
            ├── UPLOAD   → UploadTemplate
            ├── LOGIN    → LoginTemplate
            ├── SEARCH   → SearchTemplate
            ├── REGISTER → RegisterTemplate
            ├── API      → ApiTemplate
            └── FORM     → FormTemplate
```

The engine is **stateless** — it takes input, generates output, and stores nothing. No database required.

---

## Build Phases

| Phase | Description | Status |
|---|---|---|
| Engine | Plain Java rule-based generator | ✅ Complete |
| 1 | Spring Boot wrap + structured form + results table | ✅ Complete |
| 2 | CSV export | ✅ Complete |
| 3 | Excel export (Apache POI) | ✅ Complete |
| 4 | Free-form text input | Not started |
| 5 | File upload (.txt / .docx) | Not started |
| 6 | JSON export | Not started |

---

## Running the Engine (Demo)

The plain Java engine is complete and runnable. To test it locally:

```bash
mvn compile exec:java -Dexec.mainClass="com.qaassist.generator.Main"
```

This runs `Main.java` — no Spring Boot or browser needed. Output prints to the console.

---

## Running the Web App

```bash
mvn spring-boot:run
```

Open `http://localhost:8080` in your browser. Fill in the form and click **Generate Test Cases**.

---

## Learning Context

This project is being built as a hands-on Java learning project. Each component introduces a new concept — enums, POJOs, interfaces, the Strategy pattern, collections, and streams.
