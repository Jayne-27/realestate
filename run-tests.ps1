# Compile Java sources and run TestRunner
param()

# Fail on errors
$ErrorActionPreference = 'Stop'

if (Test-Path out) { Remove-Item -Recurse -Force out }
New-Item -ItemType Directory -Path out | Out-Null

javac -d out src\*.java
if ($LASTEXITCODE -ne 0) { Write-Error "Compilation failed"; exit 1 }

java -cp out realestate.TestRunner
if ($LASTEXITCODE -ne 0) { Write-Error "Test runner failed"; exit 1 }
