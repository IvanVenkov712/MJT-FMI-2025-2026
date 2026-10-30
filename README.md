# MJT-FMI-2025-2026

Java labs, homework solutions, and practice exercises for the Modern Java Technologies (MJT) course at the Faculty of Mathematics and Informatics, Sofia University, for 2025-2026.

Each exercise is a separate Java project with its own `src/` directory and IntelliJ IDEA module (`.iml`). Projects with automated tests also contain a test source directory. There is no shared Maven or Gradle build.

## Labs

| Project | Topic | Main concepts |
| --- | --- | --- |
| [Lab 01](Labs/Lab%2001/README.md) | Unique substrings and task distribution | Arrays, strings, sliding windows, dynamic programming |
| [Lab 02](Labs/Lab%2002/README.md) | Dating-show simulation | Interfaces, inheritance, polymorphism |
| [Lab 03](Labs/Lab%2003/README.md) | Semester planning | Records, sealed types, exceptions, builders and factories |
| [Lab 04](Labs/Lab%2004/README.md) | FitTrack workout planner | Collections, filters, comparators, optimization |
| [Lab 05](Labs/Lab%2005/README.md) | Event bus | Generics, subscriptions, event logs, deferred processing |
| [Lab 06](Labs/Lab%2006/README.md) | Processing pipelines | Generic composition, caching, JUnit and Mockito |
| [Lab 07](Labs/Lab%2007/README.md) | ImageKit | Image I/O, grayscale conversion, Sobel edge detection |
| [Lab 08](Labs/Lab%2008/README.md) | Order analysis | CSV parsing, streams, grouping and aggregation |
| [Lab 09](Labs/Lab%2009/README.md) | Image steganography | Threads, producer-consumer queues, bit operations |
| [Lab 10](Labs/Lab%2010/README.md) | Playlist client and server | TCP, Java NIO, command parsing, JSON |

## Homeworks

| Project | Description |
| --- | --- |
| [Homework 1: JobMatch](Homeworks/Homework%201/README.md) | Match candidates and job postings with cosine and Jaccard similarity; recommend skills and report platform statistics. |
| [Homework 2: Space Scanner](Homeworks/Homework%202/README.md) | Analyze mission and rocket datasets and encrypt a rocket reliability result with AES. |
| [Homework 3: News Feed](Homeworks/Homework%203/README.md) | Search news headlines through an HTTP API, build requests, parse JSON, and collect result pages. |
| [Exam practice](Homeworks/Exam/README.md) | A small synchronization scratchpad; the current source contains compile errors. |

## Playground

[PlayGround](PlayGround/README.md) contains two additional practice projects: [DNA Analyzer](PlayGround/02-dna-analyzer/) and [Dungeons](PlayGround/dungeons/).

## Setup

Use **JDK 25** for this checkout. Several demos use no-argument `main()` methods, and other exercises use records, sealed types, and recent collection APIs.

To work in IntelliJ IDEA:

1. Open the exercise directory as a Java project, or import its `.iml` module into an existing project.
2. Select JDK 25 and mark `src/` as a Sources Root.
3. If tests are present, mark their directory as a Test Sources Root. Homework 1 uses a directory named ` test`, with a leading space.
4. Restore any unresolved module libraries. The committed `.iml` files may reference project libraries or a local Maven cache whose configuration is not included in the repository.
5. Run the entry point or tests described in that project's README. Use the exercise directory as the working directory when loading relative data paths.

### Dependencies

Production code in Labs 01-09 and Homeworks 1-2 uses only the JDK. Lab 10 and Homework 3 also require **Gson** (`com.google.code.gson:gson`) on the compile and runtime classpaths.

Tests in Labs 06-08 and 10 and Homeworks 1-3 use **JUnit Jupiter**. Lab 06 and Homeworks 1 and 3 also use **Mockito**, including its JUnit Jupiter integration where imported. The coursework module files and bundled libraries use JUnit 6.0.0 and Mockito 5.20.0. Playground test dependencies are described separately in its README.

Some projects include a `lib/` directory, but these jars are not a repository-wide dependency setup. When configuring libraries manually, use one consistent set of JUnit versions; some bundled directories contain multiple versions of the same artifact.

### Compile from PowerShell

For a project that needs only the JDK, run the following from its directory:

```powershell
$sourceFiles = @(Get-ChildItem -Path src -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
javac -d out @sourceFiles
```

For example, first run `Set-Location "Labs/Lab 01"` from the repository root, compile, then run `java -cp out UniqueSubstringFinder`.

For Lab 10 or Homework 3, set `$gsonJar` to an existing Gson jar and compile with:

```powershell
$gsonJar = 'C:/path/to/gson.jar' # Replace with your local jar path.
$sourceFiles = @(Get-ChildItem -Path src -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
javac -cp "$gsonJar" -d out @sourceFiles
```

When launching a class that uses Gson on Windows, include both output and the jar: `java -cp "out;$gsonJar" package.ClassName`. Replace `package.ClassName` with the project's entry point. On macOS/Linux, use `:` between classpath entries.

Most projects expose an API rather than a runnable application. Use their tests or a small driver program to call those APIs. Run existing JUnit suites through the IDE after configuring their test libraries; there is no shared test runner script.
