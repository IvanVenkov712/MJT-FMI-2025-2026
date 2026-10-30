# Lab 05: Event Bus

A generic publish-subscribe event bus with event history and subscribers that can defer processing.

## Features

- Subscribe and unsubscribe handlers for an event class.
- Publish events to the registered subscribers.
- Query event logs for a time interval and inspect subscriptions.
- Collect events in a `DeferredEventSubscriber` and iterate over them by priority, then timestamp. Lower priority numbers are processed first.

The source package is `bg.sofia.uni.fmi.mjt.eventbus`. `EventBus` defines the API and `EventBusImpl` implements it. `events/` contains the event and payload contracts and comparators; `subscribers/` contains subscriber types; `exception/` defines `MissingSubscriptionException`.

## Use

Use JDK 25 and mark `src/` as the source root, or follow the [PowerShell compilation instructions](../../README.md#compile-from-powershell). No external libraries are required.

Implement `Event` and `Payload` for an event type, register a `Subscriber` with `subscribe(...)`, and call `publish(...)`. Use `DeferredEventSubscriber` when events should be stored for later iteration.

`src/Main.java` is a small `HashMap` experiment, rather than an event-bus demonstration. After compilation, it can be launched with `java -cp out Main`. This lab has no automated test source directory.

[Repository overview](../../README.md)
