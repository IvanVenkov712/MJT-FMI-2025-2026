package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.ByCalloryWorkoutComparator;
import bg.sofia.uni.fmi.mjt.fittrack.workout.comparator.ByDifficultyWorkoutComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptimalPlanGenerator {
    public OptimalPlanGenerator(List<Workout> workouts, int totalMinutes) {
        this.workouts = workouts;
        this.totalMinutes = totalMinutes;
        n = workouts.size();
        memo = new int[n + 1][totalMinutes + 1];
        included = new boolean[n + 1][totalMinutes + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
    }

    List<Workout> getOptimalPlan() {
        fillMemo();

        List<Workout> res = new ArrayList<>();
        int w = totalMinutes;

        for (int i = n; i > 0; --i) {
            if (memo[i][w] != memo[i - 1][w]) {
                res.add(workouts.get(i - 1));
                w -= workouts.get(i - 1).getDuration();
            }
        }

        res.sort(new ByCalloryWorkoutComparator().reversed()
            .thenComparing(new ByDifficultyWorkoutComparator().reversed()));
        return res;
    }

    private void fillMemo() {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= totalMinutes; j++) {
                if (i == 0 || j == 0)
                    memo[i][j] = 0;
                else {
                    int duration = workouts.get(i - 1).getDuration();
                    int calories = workouts.get(i - 1).getCaloriesBurned();
                    int pick = 0;

                    if (duration <= j) {
                        pick = calories + memo[i - 1][j - duration];
                    }
                    int notPick = memo[i - 1][j];
                    memo[i][j] = Math.max(pick, notPick);
                    included[i - 1][j] = pick > notPick;
                }
            }
        }
    }


    private final int[][] memo;
    private final boolean[][] included;
    private final List<Workout> workouts;
    private final int totalMinutes;
    private final int n;
}
