package test.java;

import dna.DNAAnalyzer;
import org.junit.Assert;
import org.junit.BeforeClass;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;

public class SampleDNAAnalyzerTest {

    private static DNAAnalyzer dnaAnalyzer;

    @BeforeClass
    public static void setUp() {
        dnaAnalyzer = new DNAAnalyzer();
    }

    @Test
    public void testDNAAnalyzer_Abracadabra() {
        Assert.assertEquals("abra", dnaAnalyzer.longestRepeatingSequence("abracadabra"));
    }

    @Test
    public void testDNAAnalyzer_DNASequence() {
        Assert.assertEquals("TACTC", dnaAnalyzer.longestRepeatingSequence("ATACTCGGTACTCT"));
    }

}