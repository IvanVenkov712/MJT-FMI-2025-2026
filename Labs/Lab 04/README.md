# Lab 04: FitTrack Workout Planner

A workout planner built around Java collections, reusable filters, comparators, and dynamic programming.

## Features

- Model cardio, strength, and yoga workouts with duration, calories, and difficulty.
- Search workouts by name, type, duration, or calories, combining multiple filters.
- Group workouts by type and retrieve sorted or unmodifiable collections.
- Generate a weekly plan that maximizes burned calories within a time budget using a 0/1 knapsack-style algorithm.

The source package is `bg.sofia.uni.fmi.mjt.fittrack`. Start with `FitPlannerAPI` and its implementation, `FitPlanner`. Workout types, filters, and comparators are in `workout/`; `OptimalPlanGenerator` performs plan selection.

## Use

Use JDK 25 and mark `src/` as the source root, or follow the [PowerShell compilation instructions](../../README.md#compile-from-powershell). The lab requires only the JDK.

Construct `FitPlanner` with a collection of workouts. Call `findWorkoutsByFilters(...)` to search or `generateOptimalWeeklyPlan(totalMinutes)` to plan. A zero-minute budget returns an empty plan; when every available workout exceeds a positive budget, the planner throws `OptimalPlanImpossibleException`.

There is no console entry point or automated test source directory in this lab.

[Repository overview](../../README.md)
