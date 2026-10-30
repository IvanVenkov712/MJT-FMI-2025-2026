# Lab 02: Dating-Show Simulation

A small simulation of participants attending dates and being eliminated from a show. The exercise practices interfaces, abstract classes, inheritance, and polymorphism.

## Structure

All classes are under `src/bg/sofia/uni/fmi/mjt/show/`.

- `ShowAPI` and `ShowAPIImpl` manage participants, play rounds, organize individual dates, and apply elimination rules.
- `ergenka/` defines the participant interface, shared state, and romantic and humorous participant types with different reactions to dates.
- `date/DateEvent` describes a date's location, tension level, and duration.
- `elimination/` contains rules based on rating, attribute totals, and public votes.

`ShowAPIImpl` accepts the participants and default elimination rules in its constructor. When no defaults are supplied, it uses `LowestRatingEliminationRule`.

## Use

Open this directory as a Java project with JDK 25 and mark `src/` as the source root. Alternatively, use the [PowerShell compilation instructions](../../README.md#compile-from-powershell).

Create participants, pass them to `ShowAPIImpl`, and call `playRound(DateEvent)` followed by `eliminateErgenkas(EliminationRule[])` to simulate the show. `getErgenkas()` returns the current participants.

This project exposes an API and has no console entry point or automated test sources. It requires only the JDK.

[Repository overview](../../README.md)
