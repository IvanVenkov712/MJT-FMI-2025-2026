package bg.sofia.uni.fmi.mjt.pipeline.stage;

import bg.sofia.uni.fmi.mjt.pipeline.step.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StageTest {
    @Test
    void testStartNullInput() {
        assertThrows(
            IllegalArgumentException.class, () -> Stage.start(null),
            "When started with null IllegalArgumentException should be thrown"
        );
    }

    @Test
    void testStartNotNullInput() {
        Step<Integer, Integer> step = mock();
        Stage<Integer, Integer> stage = Stage.start(step);
    }

    @Test
    void testAddStepNotNullStep() {
        Step<Integer, String> step1 = mock();
        Step<String, Double> step2 = mock();
        Stage<Integer, String> stage = Stage.start(step1);
        Stage<Integer, Double> stage2 = stage.addStep(step2);
        boolean areTheSame = stage == (Object) stage2;
        assertTrue(areTheSame, "addStep should return the same stage instance!");
    }

    @Test
    void testAddStepNullStep() {
        Step<Integer, String> step1 = mock();
        Stage<Integer, String> stage = Stage.start(step1);
        assertThrows(IllegalArgumentException.class, () -> stage.addStep(null),
            "IllegalArgumentException should be thrown for null step added!"
        );
    }
}