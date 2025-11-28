# Tests (JUnit 5) — how to run and extend

What these tests do
- The current tests are "existence" tests: they verify the core classes/interfaces are present and on the classpath. They are intentionally low-coupling scaffolding so you can add behavioral tests safely.

Where to place these tests
- The tests will be added under `src/test/java/`. If your project uses a package declaration in the production sources, move these tests into the same package (or update the Class.forName strings to include the package).

Adding JUnit 5
- If you use Maven, add:

<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.0</version>
  <scope>test</scope>
</dependency>

and run `mvn test`.

- If you use Gradle (Groovy DSL):

testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
test {
    useJUnitPlatform()
}

and run `./gradlew test`.

- If you don't use a build tool, add JUnit 5 jars to the classpath for compilation and test execution.

Next steps to make the tests more useful
1. Add behavioral tests that exercise public methods (e.g., adding/removing properties, loading from files, searching/sorting). To do that:
   - Inspect the public API of `RealEstate`, `RealEstateAgent`, and `Panel` and write unit tests that call their methods and assert expected outputs.
2. Add tests that load a small fixture file (you already have `realestates.txt`) and assert correct parsing/number of items.
3. Add a GitHub Actions workflow to run tests on every push — I can prepare that as a follow-up.

Troubleshooting
- ClassNotFoundException in tests: make sure test compile/run classpath includes compiled src classes and that the test's package/fully-qualified names match the actual source files.
