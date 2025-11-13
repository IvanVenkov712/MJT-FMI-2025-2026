package dna;

public class DNAAnalyzer {
    public static String longestRepeatingSequence(String dna){
        if(dna.length() <= 1){
            return "";
        }
        int n = dna.length();
        int maxEnd = 0;
        int maxLen   = 0;
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; ++i){
            dp[i][0] = dp[0][i] = dna.charAt(0) == dna.charAt(i)? 1 : 0;
        }
        for(int i = 1; i < n; ++i){
            for(int j = 1; j < n; ++j){
                dp[i][j] = dna.charAt(i) == dna.charAt(j)? 1 + dp[i - 1][j - 1] : 0;
                if(i != j && dp[i][j] > maxLen){
                    maxEnd = i;
                    maxLen = dp[i][j];
                }
            }
        }
        return dna.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }
}
