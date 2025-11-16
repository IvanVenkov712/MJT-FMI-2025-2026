package bg.sofia.uni.fmi.mjt.pipeline.stage;

import bg.sofia.uni.fmi.mjt.pipeline.step.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void testExecuteWorksOneStep() {
        Step<Integer, Integer> step1 = mock();
        when(step1.process(0)).thenReturn(5);
        Stage<Integer, Integer> stage = Stage.start(step1);
        assertEquals(5, stage.execute(0),
            "5 expected"
        );
        verify(step1).process(0);
    }

    @Test
    void testExecuteThreeStepsSameType() {
        Step<Integer, Integer> step1 = mock();
        when(step1.process(0)).thenReturn(5);

        Step<Integer, Integer> step2 = mock();
        when(step2.process(5)).thenReturn(-463);

        Step<Integer, Integer> step3 = mock();
        when(step3.process(-463)).thenReturn(18);

        Stage<Integer, Integer> stage = Stage.start(step1).addStep(step2).addStep(step3);

        assertEquals(18, stage.execute(0),
            "18 expected"
        );

        verify(step1).process(0);
        verify(step2).process(5);
        verify(step3).process(-463);

    }
}