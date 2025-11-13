package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

import java.util.Iterator;

public class DeferredEventSubscriberIterator<T extends Event<?>> implements Iterator<T> {
    public DeferredEventSubscriberIterator(Iterator<EventWrapper<T>> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next().getEvent();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    private final Iterator<EventWrapper<T>> iterator;
}
