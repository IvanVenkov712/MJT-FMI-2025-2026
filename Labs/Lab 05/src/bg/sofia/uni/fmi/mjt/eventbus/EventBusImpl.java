package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class EventBusImpl implements EventBus {
    public EventBusImpl() {
        eventLogs = new HashMap<>();
        subs = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType was null!");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber was null!");
        }
        subs.putIfAbsent(eventType, new HashSet<>());
        if (!subs.get(eventType).contains(subscriber)) {
            subs.get(eventType).add(subscriber);
        }
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType was null");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber was null");
        }
        if (!subs.get(eventType).contains(subscriber)) {
            throw new MissingSubscriptionException("subscriber is not subscribed for eventType");
        }
        subs.get(eventType).remove(subscriber);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("event was null");
        }

        // According to javadoc of getClass(), event.getClass() is of Class<? extends Event>
        // which is compatible with Class<? extends Event<?>>
        // type, so the cast will be successful and safe
        eventLogs.putIfAbsent((Class<? extends Event<?>>)event.getClass(), new ArrayList<>());
        eventLogs.get(event.getClass()).add(event);

        if (!subs.containsKey(event.getClass())) {
            return;
        }
        for (Subscriber<? extends Event<?>> sub : subs.get(event.getClass())) {
            // The cast is necessary and safe because we know that the subscriber's type is
            // a parent of T due to the way we insert the subscribers.
            ((Subscriber<? super T>)sub).onEvent(event);
        }
    }

    @Override
    public void clear() {
        subs.clear();
        eventLogs.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType was null");
        }
        if (from == null) {
            throw new IllegalArgumentException("from was null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to was null");
        }
        if (!eventLogs.containsKey(eventType)) {
            return List.of();
        }
        List<Event<?>> res = new ArrayList<>();
        for (Event<?> event : eventLogs.get(eventType)) {
            if (event.getTimestamp().compareTo(from) >= 0 && event.getTimestamp().compareTo(to) < 0) {
                res.add(event);
            }
        }
        return Collections.unmodifiableList(res);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType is null");
        }
        if (!subs.containsKey(eventType)) {
            return List.of();
        }
        return Collections.unmodifiableCollection(subs.get(eventType));
    }

    private final Map<Class<? extends Event<?>>, Collection<Event<?>>> eventLogs;
    private final Map<Class<? extends Event<?>>, Collection<Subscriber<? extends Event<?>>>> subs;

}
