package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.comparator.EventByPriorityComparator;
import bg.sofia.uni.fmi.mjt.eventbus.events.comparator.EventByTimestampComparator;

public class EventWrapper<T extends Event<?>> implements Comparable<EventWrapper<T>> {
    private static int currentCode = 0;

    public EventWrapper(T event) {
        this.event = event;
        this.code = currentCode++;
    }

    public T getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        EventWrapper<?> that = (EventWrapper<?>) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public int compareTo(EventWrapper<T> o) {
        int compareEvents = new EventByPriorityComparator<>()
            .thenComparing(new EventByTimestampComparator<>()).compare(event, o.event);
        if (compareEvents != 0) {
            return compareEvents;
        }
        return Integer.compare(code, o.code);
    }

    private final T event;
    private final int code;
}