package softwareengineering.Solver;

import java.util.HashSet;
import java.util.Set;

public class SingleCandidateRule implements DeductionRule {
    @Override
    public boolean apply(int[][] board) {
        boolean changed = false;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    Set<Integer> possibleValues = getPossibleValues(board, row, col);
                    if (possibleValues.size() == 1) {
                        board[row][col] = possibleValues.iterator().next();
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private Set<Integer> getPossibleValues(int[][] board, int row, int col) {
        Set<Integer> possibleValues = new HashSet<>();
        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                possibleValues.add(num);
            }
        }
        return possibleValues;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
