# Exam Practice: Synchronization Scratchpad

This directory contains a small Java experiment in `src/Main.java` and an IntelliJ IDEA module. It explores instance methods, a mutex object, synchronized blocks, and notification.

## Current state

The source is a scratchpad and does not currently compile: its static `main()` references `this` and the instance field `mutex`. The notification call also needs review: the synchronized block holds `mutex`, while the unqualified `notifyAll()` call targets the `Main` instance.

Use JDK 25 and open this directory as a Java project with `src/` as the source root. To inspect the current compiler diagnostics from this directory:

```powershell
javac -d out src/Main.java
```

There are no external dependencies or automated tests. The source must be corrected before it can be run successfully.

[Repository overview](../../README.md)
