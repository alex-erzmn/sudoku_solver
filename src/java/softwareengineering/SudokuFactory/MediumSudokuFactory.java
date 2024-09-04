package softwareengineering.SudokuFactory;

public class MediumSudokuFactory extends SudokuFactory {
    @Override
    public int[][] createSudoku() {
        int[][] board = createFilledSudoku();
        removeNumbers(board, SIZE * SIZE - 30); // Behalte 30 Hinweise, um ein mittleres Puzzle zu erstellen
        return board;
    }

    private int[][] createFilledSudoku() {
        int[][] board = new int[SIZE][SIZE];
        if (fillSudoku(board)) {
            return board;
        } else {
            throw new RuntimeException("Failed to generate a valid Sudoku board");
        }
    }
}
