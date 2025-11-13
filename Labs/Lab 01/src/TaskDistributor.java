import java.util.Arrays;

public class TaskDistributor {
    static void main() {
        System.out.println("I am very kind person!");
        int[][] test = new int[7][];
        test[0] = new int[]{1,2,3,4,5};
        test[1] = new int[]{10,20,15,5};
        test[2] = new int[]{7,3,2,1,5,4};
        test[3] = new int[]{9,1,1,1};
        test[4] = new int[]{};
        test[5] = new int[]{120};
        test[6] = new int[]{30, 30};
        for(int i = 0; i < test.length; ++i){
            System.out.println(minDifference(test[i]));
        }
    }
    public static int minDifference(int[] tasks){
        int total = 0;
        for(int i = 0; i < tasks.length; ++i){
            total += tasks[i];
        }
        int target = total / 2;
        boolean[] prev = new boolean[target + 1];
        boolean[] curr = new boolean[target + 1];
        prev[0] = curr[0] = true;
        for(int i = 0; i < tasks.length; ++i){
            for(int j = 0; j <= target; ++j){
                if(j < tasks[i]){
                    curr[j] = prev[j];
                }
                else{
                    curr[j] = prev[j] || prev[j - tasks[i]];
                }
            }
            prev = curr;
            curr = new boolean[target + 1];
        }
        int best = target;
        while(!prev[best]){
            --best;
        }
        return total - 2 * best;
    }
}
