package bg.sofia.uni.fmi.mjt.pipeline;

import bg.sofia.uni.fmi.mjt.pipeline.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PipelineTest {
    @Test
    void testStartNullInitialStage() {
        assertThrows(IllegalArgumentException.class, () -> Pipeline.start(null),
            "start should throw IllegalArgumentException when null InitialState is passed!"
        );
    }

    @Test
    void testAddStageNullStage() {
        Stage<Integer, Integer> stage = mock();
        Pipeline<Integer, Integer> pipeline = Pipeline.start(stage);
        assertThrows(IllegalArgumentException.class, () -> pipeline.addStage(null),
            "addStage should throw IllegalArgumentException when null stage is passed!"
        );
    }

    @Test
    void testExecuteEmptyCacheOneStage() {
        Stage<Integer, Integer> stage = mock();
        when(stage.execute(0)).thenReturn(5);

        Cache emptyCache = mock();
        when(emptyCache.containsKey(anyInt())).thenReturn(false);

        Pipeline<Integer, Integer> pipeline = new Pipeline<>(List.of(stage), emptyCache);
        assertEquals(5, pipeline.execute(0),
            "5 expected!"
        );
    }

    @Test
    void testExecuteEmptyCacheTwoStages() {
        Stage<Integer, Integer> stage1 = mock();
        when(stage1.execute(0)).thenReturn(5);

        Stage<Integer, Integer> stage2 = mock();
        when(stage2.execute(5)).thenReturn(8);

        Cache emptyCache = mock();
        when(emptyCache.containsKey(anyInt())).thenReturn(false);

        Pipeline<Integer, Integer> pipeline = new Pipeline<>(List.of(stage1, stage2), emptyCache);
        assertEquals(8, pipeline.execute(0),
            "5 expected!"
        );
    }

    @Test
    void testExecuteNonEmptyCacheOneStage() {
        Stage<Integer, Integer> stage = mock();

        Cache cache = mock();
        when(cache.containsKey(0)).thenReturn(true);
        when(cache.getCachedValue(0)).thenReturn(5);

        Pipeline<Integer, Integer> pipeline = new Pipeline<>(List.of(stage), cache);
        assertEquals(5, pipeline.execute(0),
            "5 expected!"
        );

        verify(cache).containsKey(0);
        verify(cache).getCachedValue(0);
    }

    @Test
    void testExecuteNonEmptyCacheTwoStages() {
        Stage<Integer, Integer> stage1 = mock();
        Stage<Integer, Integer> stage2 = mock();

        Cache cache = mock();
        when(cache.containsKey(0)).thenReturn(true);
        when(cache.getCachedValue(0)).thenReturn(5);

        Pipeline<Integer, Integer> pipeline = new Pipeline<>(List.of(stage1, stage2), cache);
        assertEquals(5, pipeline.execute(0),
            "5 expected!"
        );

        verify(cache).containsKey(0);
        verify(cache).getCachedValue(0);
    }

    @Test
    void testExecuteValueBeingCached() {
        Stage<Integer, Integer> stage1 = mock();
        when(stage1.execute(0)).thenReturn(5);

        Stage<Integer, Integer> stage2 = mock();
        when(stage2.execute(5)).thenReturn(8);

        Cache emptyCache = mock();
        when(emptyCache.containsKey(anyInt())).thenReturn(false);

        Pipeline<Integer, Integer> pipeline = new Pipeline<>(List.of(stage1, stage2), emptyCache);
        assertEquals(8, pipeline.execute(0),
            "5 expected!"
        );

        verify(emptyCache).cacheValue(0, 8);
    }
}