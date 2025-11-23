package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.comparator.EventByPriorityComparator;
import bg.sofia.uni.fmi.mjt.eventbus.events.comparator.EventByTimestampComparator;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {

    public static final Comparator<? super Event<?>> DEFAULT_COMPARATOR =
        new EventByPriorityComparator<>().thenComparing(new EventByTimestampComparator<>());

    private final List<T> events;
    boolean eventsIsSorted = false;

    public DeferredEventSubscriber() {
        events = new ArrayList<>();
    }

    /**
     * Store an event for processing at a later time.
     *
     * @param event the event to be processed
     * @throws IllegalArgumentException if the event is null
     */
    @Override
    public void onEvent(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event is null");
        }
        events.add(event);
        eventsIsSorted = false;
    }

    /**
     * Get an iterator for the unprocessed events. The iterator should provide the events sorted
     * by priority, with higher-priority events first (lower priority number = higher priority).
     * For events with equal priority, earlier events (by timestamp) come first.
     *
     * @return an iterator for the unprocessed events
     */
    @Override
    public Iterator<T> iterator() {
        if (!eventsIsSorted) {
            events.sort(DEFAULT_COMPARATOR);
            eventsIsSorted = true;
        }
        return events.iterator();
    }

    /**
     * Check if there are unprocessed events.
     *
     * @return true if there are unprocessed events, false otherwise
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }
}
