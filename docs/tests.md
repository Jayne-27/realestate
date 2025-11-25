**Tests & Screenshots**

This document describes how to run the lightweight test harness included in the repository and where the test output and screenshots are saved.

- **What the test does:**
  - Instantiates several `RealEstate` and `Panel` objects (same cases as `MainTest`).
  - Captures textual input and result output to `docs/test-output/output.txt`.
  - Renders the same textual output into a PNG image at `docs/screenshots/test-output.png`.

- **How to run (PowerShell on Windows):**

```powershell
cd C:\Users\DELL\IdeaProjects\realestate
.\run-tests.ps1
```

- **Where results appear:**
  - Text output: `docs/test-output/output.txt`
  - Screenshot: `docs/screenshots/test-output.png`

- **Sample input (what the harness uses):**
  - `RealEstate("Budapest", 250000, 100, 4, Genre.CONDOMINIUM)`
  - `RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE)`
  - `Panel("Budapest", 180000, 70, 3, Genre.CONDOMINIUM, 4, false)`
  - `Panel("Debrecen", 120000, 35, 2, Genre.CONDOMINIUM, 0, true)`

- **Notes:**
  - The `TestRunner` will create the `docs/test-output` and `docs/screenshots` directories if they do not exist.
  - The generated PNG is a simple rendering of the textual results and is suitable for embedding in documentation or README files.

If you want automated test assertions (JUnit) or additional test cases, tell me and I will add them.
