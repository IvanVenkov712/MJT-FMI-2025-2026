package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileSystemImageManagerTest {

    LocalFileSystemImageManager manager;
    @BeforeEach
    void setUp() {
        manager = new LocalFileSystemImageManager();
    }
    @Test
    void testLoadImageNullImageFile() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null),
            "should throw IllegalArgumentException with null imageFile");
    }

    @Test
    void testLoadImageNonExistingFile() {
        assertThrows(IOException.class, () -> manager.loadImage(new File("nanaisiferodo")),
            "should throw IOException on non-existing file");
    }

    @Test
    void testLoadImageNotRegularFile() {
        assertThrows(IOException.class, () -> manager.loadImage(new File(".")),
            "should throw IOException when file is not regular");
    }

    @Test
    void testLoadImageNotNotSupportedFormat() {
        assertThrows(IOException.class, () -> manager.loadImage(new File("dummy.txt")),
            "should throw IOException when file is not in the supported format.");
    }

    @Test
    void testLoadImagesFromDirectoryNullDirectory() {
        assertThrows(IllegalArgumentException.class, () -> manager.loadImagesFromDirectory(null),
            "should throw IllegalArgumentException with null directory");
    }

    @Test
    void testLoadImagesFromDirectoryNotExisting() {
        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(new File("nanaisiferodo")),
            "should throw IOException with non-existing directory");
    }

    @Test
    void testLoadImagesFromDirectoryNotExisting() {
        assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(new File("nanaisiferodo")),
            "should throw IOException with non-existing directory");
    }
}