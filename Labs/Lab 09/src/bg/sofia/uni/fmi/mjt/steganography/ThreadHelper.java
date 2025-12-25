package bg.sofia.uni.fmi.mjt.steganography;

import java.util.List;

public class ThreadHelper {
    public static void joinProducers(List<Thread> producers) {
        producers.forEach(
            consumer -> {
                try {
                    consumer.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }
}
