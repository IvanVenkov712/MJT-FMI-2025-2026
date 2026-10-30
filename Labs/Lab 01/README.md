# Lab 01: Strings and Task Distribution

Two introductory algorithm exercises using strings, arrays, and dynamic programming.

## Exercises

| Class | Method | Purpose |
| --- | --- | --- |
| `UniqueSubstringFinder` | `longestUniqueSubstring(String)` | Find the longest contiguous substring without repeated characters using a sliding window. |
| `TaskDistributor` | `minDifference(int[])` | Split task durations between two groups while minimizing the difference in their total durations, using subset-sum dynamic programming. |

For example, `longestUniqueSubstring("pwwkew")` returns `"wke"`, and `minDifference(new int[] {1, 2, 3, 4, 5})` returns `1`.

The substring implementation uses a 256-entry character table, so inputs must have character values below 256. Task durations should be non-negative integers.

## Run

Use JDK 25. From this directory in PowerShell:

```powershell
javac -d out src/UniqueSubstringFinder.java src/TaskDistributor.java
java -cp out UniqueSubstringFinder
java -cp out TaskDistributor
```

Both classes include a no-argument `main()` with sample inputs and console output. There are no external dependencies or automated test sources in this lab.

[Repository overview and setup](../../README.md)
