package softwareengineering.Solver;

import java.util.HashSet;
import java.util.Set;

public class HiddenSingleRule implements DeductionRule{
    @Override
    public boolean apply(int[][] board) {
        boolean changed = false;

        for (int num = 1; num <= 9; num++) {
            for (int row = 0; row < 9; row++) {
                Set<Integer> possibleCols = new HashSet<>();
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0 && isSafe(board, row, col, num)) {
                        possibleCols.add(col);
                    }
                }
                if (possibleCols.size() == 1) {
                    board[row][possibleCols.iterator().next()] = num;
                    changed = true;
                }
            }

            for (int col = 0; col < 9; col++) {
                Set<Integer> possibleRows = new HashSet<>();
                for (int row = 0; row < 9; row++) {
                    if (board[row][col] == 0 && isSafe(board, row, col, num)) {
                        possibleRows.add(row);
                    }
                }
                if (possibleRows.size() == 1) {
                    board[possibleRows.iterator().next()][col] = num;
                    changed = true;
                }
            }

            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 3; blockCol++) {
                    Set<int[]> possiblePositions = new HashSet<>();
                    for (int row = blockRow * 3; row < blockRow * 3 + 3; row++) {
                        for (int col = blockCol * 3; col < blockCol * 3 + 3; col++) {
                            if (board[row][col] == 0 && isSafe(board, row, col, num)) {
                                possiblePositions.add(new int[]{row, col});
                            }
                        }
                    }
                    if (possiblePositions.size() == 1) {
                        int[] pos = possiblePositions.iterator().next();
                        board[pos[0]][pos[1]] = num;
                        changed = true;
                    }
                }
            }
        }
        return changed;
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
