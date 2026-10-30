# Lab 06: Generic Processing Pipelines

A generic pipeline abstraction that composes processing steps into stages and caches results for previously processed inputs. The lab also includes an in-memory text-file model and example transformations.

## Structure

- `pipeline/step/Step<I, O>` defines a single transformation.
- `pipeline/stage/Stage<I, O>` composes steps with `start(...)` and `addStep(...)`.
- `pipeline/Pipeline<I, O>` groups stages and executes them with a result cache.
- `pipeline/Cache` stores input-output pairs.
- `file/` contains a text container and steps for validation, uppercasing, splitting, printing, and counting. This `File` class represents text in memory.

All packages begin with `bg.sofia.uni.fmi.mjt`.

## Use and tests

Use JDK 25. Production sources in `src/` require only the JDK and can be compiled with the [shared PowerShell instructions](../../README.md#compile-from-powershell).

Create a stage with `Stage.start(step)`, append compatible steps, wrap it with `Pipeline.start(stage)`, and call `execute(input)`. There is no console entry point.

Mark `test/` as a test source root and configure JUnit Jupiter and Mockito, including `mockito-junit-jupiter`. The bundled `lib/` directory includes JUnit 6.0.0 and Mockito 5.20.0 jars and their dependencies; use a consistent set of versions when adding them to the test classpath.

Run the test directory in the IDE. Suites cover the pipeline, stages, cache, text container, and individual processing steps.

[Repository overview and dependency setup](../../README.md)
