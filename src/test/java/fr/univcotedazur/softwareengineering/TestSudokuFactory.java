package fr.univcotedazur.softwareengineering;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;


public class TestSudokuFactory {


    private TestSudokuFactory() {
    }

    private static final class InstanceHolder {
        private static final TestSudokuFactory instance = new TestSudokuFactory();
    }

    public static TestSudokuFactory getInstance() {
        return TestSudokuFactory.InstanceHolder.instance;
    }


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
        sudoku.initializeGrid(grid);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Beispiel für ein leichtes Sudoku
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
        sudoku.initializeGrid(grid);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    // Beispiel für ein schweres Sudoku
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
        sudoku.initializeGrid(grid);
        sudoku.initializePossibleValues();
        return sudoku;
    }

}
