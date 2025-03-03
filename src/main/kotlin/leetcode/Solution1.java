package leetcode;

public class_lecture Solution1 {
    public int minMoves(String S) {
        int moves = 0;
        int count = 1;

        for (int i = 1; i < S.length(); i++) {
            if (S.charAt(i) == S.charAt(i - 1)) {
                count++;
            } else {
                count = 1; // Reset count when the character changes
            }

            // When there are 3 or more consecutive characters, we need to make a move
            if (count == 3) {
                moves++;
                count = 2; // Decrement count to check for additional groups of three
            }
        }

        return moves;
    }

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.minMoves("baaaaa")); // Output: 1
        System.out.println(solution.minMoves("baabbaaabbbba")); // Output: 2
        System.out.println(solution.minMoves("baabab")); // Output: 0
    }
}


