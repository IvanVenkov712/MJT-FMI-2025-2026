package bg.sofia.uni.fmi.mjt.steganography.queue;

import java.util.ArrayList;
import java.util.List;

public class MyArrayBlockingQueue<T> implements BlockingQueue<T> {
    private final int capacity;
    private final List<T> elems;

    public MyArrayBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.elems = new ArrayList<>(capacity);
    }

    @Override
    public void put(T elem) throws InterruptedException {

    }

    @Override
    public T take() throws InterruptedException {
        return null;
    }
}
