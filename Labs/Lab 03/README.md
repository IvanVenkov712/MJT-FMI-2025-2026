# Lab 03: Semester Planning

A semester planner for Computer Science and Software Engineering students. It models subjects, category requirements, credits, study time, and rest time while practicing records, sealed hierarchies, custom exceptions, builders, and factories.

## Structure

The source package is `bg.sofia.uni.fmi.mjt.burnout`.

- `plan/SemesterPlan` holds the available subjects, category requirements, and minimum credits.
- `subject/` contains subject models, categories, requirements, comparators, and subject-list builders and factories.
- `semester/SemesterPlannerAPI` defines subject selection and the exercise's food-jar calculation.
- `ComputerScienceSemesterPlanner` and `SoftwareEngineeringSemesterPlanner` select their corresponding builder implementations.
- `exception/` contains errors for invalid requirements, insufficient credits, and excessive rest time.

The Computer Science builder orders subjects by rating; the Software Engineering builder uses credits and category requirements. `calculateJarCount(...)` computes a food-jar count from study time, rest limits, and semester duration.

## Use

Use JDK 25 and mark `src/` as the source root, or follow the [PowerShell compilation instructions](../../README.md#compile-from-powershell). No external libraries are required.

Build a `SemesterPlan`, instantiate the planner for the chosen degree, and call `calculateSubjectList(plan)`. Pass the selected subjects to `calculateJarCount(...)` for the additional calculation.

There is no console entry point or automated test source directory in this lab.

[Repository overview](../../README.md)
