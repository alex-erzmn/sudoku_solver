package softwareengineering.SudokuFactory;

public class HardSudokuFactory extends SudokuFactory {
    @Override
    public int[][] createSudoku() {
        int[][] board = createFilledSudoku();
        removeNumbers(board, SIZE * SIZE - 20); // Behalte 20 Hinweise, um ein schwieriges Puzzle zu erstellen
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
