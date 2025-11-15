package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.Utils;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SplitFileTest {
    @Test
    void testProcessNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new SplitFile().process(null),
            "SplitFile.process called with null file should throw IllegalArgumentException"
        );
    }

    @Test
    void testProcessEmptyInput() {
       assertIterableEquals(Set.of(), new SplitFile().process(new File("")),
           "Empty file should return empty set!"
       );
    }

    @Test
    void testProcessOneWordInput() {
        assertIterableEquals(Set.of(new File("Ivan")), new SplitFile().process(new File("Ivan")),
            "Expected result: { Ivan }"
        );
    }

    @Test
    void testProcessTwoWordInput() {
        Set<File> set1 = Set.of(new File("Ivan"), new File("Is"));
        Set<File> set2 = new SplitFile().process(new File("Ivan Is"));

        assertTrue(Utils.areSetsEqual(set1, set2), "Expected result: { Ivan Is }");
    }

    @Test
    void testProcessMoreWordsInput() {
        Set<File> set1 = Set.of(new File("Ivan"), new File("Is"), new File("Very"), new File("Cool"));
        Set<File> set2 = new SplitFile().process(new File("Ivan Is Very Cool"));

        assertTrue(Utils.areSetsEqual(set1, set2), "Expected result: { Ivan Is Very Cool }");
    }

}