package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("imageFile is null");
        }
        BufferedImage res = ImageIO.read(imageFile);
        if (res == null) {
            throw new IOException("null returned!");
        }
        return res;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("imagesDirectory is null");
        }
        List<BufferedImage> res = new ArrayList<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(imagesDirectory.toPath())) {
            for (Path entry : dirStream) {
                res.add(ImageIO.read(entry.toFile()));
            }
        }
        return res;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
        if (imageFile == null) {
            throw new IllegalArgumentException("imageFile is null");
        }
        if (imageFile.exists()) {
            throw new IOException("File already exists");
        }
        ImageIO.write(image, "png", imageFile);
    }
}
