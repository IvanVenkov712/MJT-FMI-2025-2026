package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class MJTSpaceScannerTest {
    MJTSpaceScanner spaceScanner;
    @Test
    void loadWholeDataset() throws IOException {
        try(Reader missions = new FileReader("dataset/all-missions-from-1957.csv");
            Reader rockets = new FileReader("dataset/all-rockets-from-1957.csv")) {
            spaceScanner = new MJTSpaceScanner(missions, rockets, (SymmetricBlockCipher) null);
        }
    }
}