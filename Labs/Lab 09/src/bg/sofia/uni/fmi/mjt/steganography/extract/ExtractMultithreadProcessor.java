package bg.sofia.uni.fmi.mjt.steganography.extract;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;
import bg.sofia.uni.fmi.mjt.steganography.ThreadHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ExtractMultithreadProcessor {
    private final Path sourceDirectory;
    private final Path outputDirectory;
    private final int countConsumers;
    private final BlockingQueue<ExtractImageInfo> workingQueue;

    public ExtractMultithreadProcessor(Path sourceDirectory, Path outputDirectory, int countConsumers) {
        this.sourceDirectory = sourceDirectory;
        this.outputDirectory = outputDirectory;
        this.countConsumers = countConsumers;
        this.workingQueue = new LinkedBlockingQueue<>();
    }

    public void extractPNGImages() {
        List<Thread> producers = startProducers();
        List<Thread> consumers = startConsumers();
        ThreadHelper.joinAll(producers);
        putPoisonPills();
        ThreadHelper.joinAll(consumers);
    }

    private List<Thread> startProducers(List<Path> sourceImgs) {
        List<Thread> res = new ArrayList<>();
        for (Path sourceImg : sourceImgs) {
            Thread curr = new Thread(new ExtractProducer(sourceImg, workingQueue));
            curr.start();
            res.add(curr);
        }
        return res;
    }

    private List<Thread> startProducers() {
        List<Path> sourceImgs = ImageHelper.getPNGs(sourceDirectory);
        return startProducers(sourceImgs);
    }

    private List<Thread> startConsumers() {
        List<Thread> res = new ArrayList<>();
        for (int i = 0; i < countConsumers; ++i) {
            Thread curr = new Thread(new ExtractConsumer(outputDirectory, workingQueue));
            curr.start();
            res.add(curr);
        }
        return res;
    }

    private void putPoisonPills() {
        for (int i = 0; i < countConsumers; ++i) {
            try {
                workingQueue.put(ExtractImageInfo.POISON_PILL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
