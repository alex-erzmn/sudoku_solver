package fr.univcotedazur.softwareengineering;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import static fr.univcotedazur.softwareengineering.sudoku.Sudoku.SIZE;

/**
 * Factory class to create test sudokus.
 * This class is used to create test sudokus for the unit tests.
 * @since 17/09/2024
 * @see Sudoku
 */
public class TestSudokuFactory {

    private TestSudokuFactory() {
    }

    private static final class InstanceHolder {
        private static final TestSudokuFactory instance = new TestSudokuFactory();
    }

    public static TestSudokuFactory getInstance() {
        return TestSudokuFactory.InstanceHolder.instance;
    }

    // Example for an empty sudoku
    public Sudoku createEmptySudoku() {
        int[][] grid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Sudoku sudoku = new Sudoku();
        fillSudokuFromGrid(grid, sudoku);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Example for an easy Sudoku
    public Sudoku createEasySudoku() {
        int[][] grid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        Sudoku sudoku = new Sudoku();
        fillSudokuFromGrid(grid, sudoku);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Example for a hard Sudoku
    public Sudoku createHardSudoku() {
        int[][] grid = {
                {0, 0, 0, 0, 0, 0, 8, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        Sudoku sudoku = new Sudoku();
        fillSudokuFromGrid(grid, sudoku);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Example for a hard Sudoku
    public Sudoku createInvalidSudoku() {
        int[][] grid = {
                {0, 0, 0, 0, 0, 0, 8, 0, 0},
                {0, 0, 3, 6, 0, 6, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        Sudoku sudoku = new Sudoku();
        fillSudokuFromGrid(grid, sudoku);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Example for a solved Sudoku
    public Sudoku createSolvedSudoku() {
        int[][] grid = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        Sudoku sudoku = new Sudoku();
        fillSudokuFromGrid(grid, sudoku);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    /**
     * Method to fill a Sudoku with the given grid to create test sudokus.
     * @param grid      the grid to fill the sudoku with
     * @param sudoku    the sudoku to fill
     * @implNote This method is not tested because it uses the sudoku class which is tested separately.
     */
    private void fillSudokuFromGrid(int[][] grid, Sudoku sudoku) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    sudoku.setValue(row, col, value);
                }
            }
        }
    }

}
