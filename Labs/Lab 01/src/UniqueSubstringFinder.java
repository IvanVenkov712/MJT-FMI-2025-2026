import java.util.Arrays;

public class UniqueSubstringFinder {
    public static void main() {
        String[] strs = {"abc", "bbbbb", "pwwkew", "abcdefg", "x", ""};
        for(int i = 0; i < strs.length; ++i){
            System.out.println('"' + longestUniqueSubstring(strs[i]) + '"');
        }
    }
    public static String longestUniqueSubstring(String s){
        if(s.length() <= 1){
            return s;
        }
        int[] lastOccurred = new int[256];
        Arrays.fill(lastOccurred, -1);
        int longestStart = 0;
        int longestEnd = 0;
        int start = 0;
        int end = 1;
        lastOccurred[s.charAt(start)] = 0;
        for(; end < s.length(); ++end){
            if(lastOccurred[s.charAt(end)] >= 0){
               //start = lastOccurred[s.charAt(end)] + 1;
                start = Math.max(start, lastOccurred[s.charAt(end)] + 1);
            }
            lastOccurred[s.charAt(end)] = end;
            if(end - start > longestEnd - longestStart){
                longestStart = start;
                longestEnd = end;
            }
        }
        return s.substring(longestStart, longestEnd + 1);
    }
}
