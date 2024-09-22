package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

/**
 * Interface to observe a sudoku
 * @implNote Observer pattern
 */
public interface SudokuObserver {

    /**
     * Update the sudoku
     * @param sudoku the sudoku to update
     */
    void updateSudoku(Sudoku sudoku);
}