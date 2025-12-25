package bg.sofia.uni.fmi.mjt.steganography.embed;

import bg.sofia.uni.fmi.mjt.steganography.ImageHelper;
import bg.sofia.uni.fmi.mjt.steganography.ThreadHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EmbedMultithreadProcessor {
    private final BlockingQueue<EmbedImageInfo> workingQueue;
    private final Path coverSourceDirectory;
    private final Path secretSourceDirectory;
    private final Path outputDirectory;
    private final int countConsumers;

    public EmbedMultithreadProcessor(Path coverSourceDirectory,
                                     Path secretSourceDirectory, Path outputDirectory, int countConsumers) {
        this.workingQueue = new LinkedBlockingQueue<>();
        this.coverSourceDirectory = coverSourceDirectory;
        this.secretSourceDirectory = secretSourceDirectory;
        this.outputDirectory = outputDirectory;
        this.countConsumers = countConsumers;
    }

    public void embedPNGImages() {
        List<Thread> producers = startProducers();
        List<Thread> consumers = startConsumers();
        ThreadHelper.joinProducers(producers);
        putPoisonPills();
    }

    private List<Thread> startProducers(List<Path> coverImgs, List<Path> secretImgs) {
        List<Thread> res = new ArrayList<>();
        for (int i = 0; i < Math.min(coverImgs.size(), secretImgs.size()); ++i) {
            Thread curr = new Thread(new EmbedProducer(coverImgs.get(i), secretImgs.get(i), workingQueue));
            curr.start();
            res.add(curr);
        }
        return res;
    }

    private List<Thread> startProducers() {
        List<Path> coverImgs = ImageHelper.getPNGs(coverSourceDirectory);
        List<Path> secretImgs = ImageHelper.getPNGs(secretSourceDirectory);
        return startProducers(coverImgs, secretImgs);
    }

    private List<Thread> startConsumers() {
        List<Thread> res = new ArrayList<>();
        for (int i = 0; i < countConsumers; ++i) {
            Thread curr = new Thread(new EmbedConsumer(outputDirectory, workingQueue));
            curr.start();
            res.add(curr);
        }
        return res;
    }

    private void putPoisonPills() {
        for (int i = 0; i < countConsumers; ++i) {
            try {
                workingQueue.put(EmbedImageInfo.POISON_PILL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
