# Java Playground

Additional Java practice projects alongside the course labs and homeworks. Each directory is an independent IntelliJ IDEA module.

| Project | Description | Test location |
| --- | --- | --- |
| [DNA Analyzer](02-dna-analyzer/) | `dna.DNAAnalyzer.longestRepeatingSequence(String)` searches for the longest repeated contiguous sequence using dynamic programming. | `02-dna-analyzer/src/test/java/` |
| [Dungeons](dungeons/) | A grid-based game engine with hero movement, enemies, combat, weapons, spells, and potions. | `dungeons/src/test/java/` |

## Setup and tests

Use JDK 25 and open the chosen project directory. Source classes are under `src/dna/` for DNA Analyzer and `src/bg/sofia/uni/fmi/mjt/dungeon/` for Dungeons. Mark `src/test/java/` separately as a test source root.

Both projects expose APIs and have no console entry point. Production code requires only the JDK.

Dungeons tests use JUnit 4. Its `lib/` directory includes JUnit 4.13.1 and Hamcrest, alongside JUnit Jupiter 5.8.1 jars. The DNA Analyzer sample test mixes JUnit 4 imports with JUnit Jupiter's `@Test` annotation, so both libraries are needed to compile that test.

[Repository overview and setup](../README.md)
