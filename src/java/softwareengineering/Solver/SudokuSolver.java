package softwareengineering.Solver;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private final List<DeductionRule> rules;

    public SudokuSolver() {
        this.rules = new ArrayList<>();
    }

    public void addRule(DeductionRule rule) {
        this.rules.add(rule);
    }

    public boolean solve(int[][] board) {
        boolean progress;
        do {
            progress = false;
            for (DeductionRule rule : rules) {
                if (rule.apply(board)) {
                    progress = true;
                }
            }
        } while (progress);

        return isSolved(board);
    }

    private boolean isSolved(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
