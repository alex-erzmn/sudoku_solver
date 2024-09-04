package softwareengineering.SudokuFactory;

public class EasySudokuFactory extends SudokuFactory {
    @Override
    public int[][] createSudoku() {
        int[][] board = createFilledSudoku();
        removeNumbers(board, SIZE * SIZE - 40); // Behalte 40 Hinweise, um ein einfaches Puzzle zu erstellen
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