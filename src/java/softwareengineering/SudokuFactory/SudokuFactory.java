package softwareengineering.SudokuFactory;

import java.util.Random;

public abstract class SudokuFactory {
    protected static final int SIZE = 9;
    protected static final int SUBGRID_SIZE = 3;


    public abstract int[][] createSudoku();

    protected static void removeNumbers(int[][] board, int cellsToRemove) {
        Random rand = new Random();
        while (cellsToRemove > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    protected static boolean fillSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    Random rand = new Random();
                    int[] numbers = rand.ints(1, SIZE + 1).distinct().limit(SIZE).toArray();
                    for (int num : numbers) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (fillSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    protected static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printSudoku(int[][] board) {
        for (int r = 0; r < SIZE; r++) {
            for (int d = 0; d < SIZE; d++) {
                System.out.print(board[r][d]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((r + 1) % SUBGRID_SIZE == 0) {
                System.out.print("");
            }
        }
    }

}

