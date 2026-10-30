# Homework 1: JobMatch

An in-memory platform for registering candidates and employers, publishing job postings, and finding matches based on skills.

## Features

- Rank candidates for a job and job postings for a candidate.
- Find similar candidates using cosine or Jaccard similarity.
- Recommend skills that improve a candidate's matches.
- Report platform statistics, including common skills and the highest-paid job.

The source package is `bg.sofia.uni.fmi.mjt.jobmatch`. `api/JobMatchAPI` is the public contract, implemented by `JobMatch`. `model/entity/` contains candidates, employers, skills, education, and postings; `matching/` contains similarity strategies. Generic conversion, filtering, and ranking helpers support the matching logic.

## Setup and use

Use JDK 25. Production code requires only the JDK and can be compiled using the [shared PowerShell instructions](../../README.md#compile-from-powershell). There is no application entry point; call the API from a driver program, JShell, or tests.

For example:

```java
import java.util.Set;
import bg.sofia.uni.fmi.mjt.jobmatch.JobMatch;
import bg.sofia.uni.fmi.mjt.jobmatch.matching.CosineSimilarity;
import bg.sofia.uni.fmi.mjt.jobmatch.model.entity.*;

var platform = new JobMatch();
platform.registerEmployer(new Employer("Example Ltd", "jobs@example.com"));
platform.registerCandidate(new Candidate(
    "Alex", "alex@example.com", Set.of(new Skill("Java", 4)), Education.BACHELORS, 2));
platform.postJobPosting(new JobPosting(
    "java-1", "Java Developer", "jobs@example.com", Set.of(new Skill("Java", 3)),
    Education.BACHELORS, 1, 3000.0));

System.out.println(platform.findTopNJobsForCandidate(
    "alex@example.com", 5, new CosineSimilarity()));
```

An employer must be registered before its job postings are published. `JaccardSimilarity` can be supplied in place of `CosineSimilarity`.

## Tests

The test directory is named **` test`**, with a leading space. Preserve that name when marking it as a Test Sources Root or referencing it in scripts.

Tests use JUnit Jupiter and Mockito, including `mockito-junit-jupiter`. The `lib/` directory includes JUnit 6.0.0, Mockito 5.20.0, and supporting jars, alongside some older JUnit artifacts; configure a consistent version set. Run the test directory in the IDE. Suites cover the platform and both similarity strategies.

## Docker files

`Dockerfile`, `compose.yaml`, and [README.Docker.md](README.Docker.md) are scaffold files. The Dockerfile expects `pom.xml`, `mvnw`, and `.mvn/`, which are absent from this project, so the documented Docker build cannot run from this checkout as supplied. Use the Java project setup above.

[Repository overview](../../README.md)
