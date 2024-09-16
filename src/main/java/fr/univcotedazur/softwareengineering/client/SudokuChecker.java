package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import fr.univcotedazur.softwareengineering.sudoku.components.Cell;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class checks if a Sudoku is valid. A Sudoku is valid if it does not contain any duplicate numbers in any row,
 * column or box. This class implements the SudokuObserver interface and is notified whenever a Sudoku is updated.
 * @since 12/09/2024
 */
public class SudokuChecker implements SudokuObserver {

    @Override
    public void updateSudoku(Sudoku sudoku) {
        if (!isValidSudoku(sudoku)) {
            JOptionPane.showMessageDialog(null,
                    "The Sudoku is invalid. Please start a new Sudoku.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidSudoku(Sudoku sudoku) {
        // Überprüfe jede Zeile
        for (int row = 0; row < 9; row++) {
            if (!isValidGroup(sudoku.getRow(row).getCells())) {
                return false;
            }
        }

        // Überprüfe jede Spalte
        for (int col = 0; col < 9; col++) {
            if (!isValidGroup(sudoku.getColumn(col).getCells())) {
                return false;
            }
        }

        // Überprüfe jede Box
        for (int box = 0; box < 9; box++) {
            if (!isValidGroup(sudoku.getBox(box).getCells())) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidGroup(List<Cell> cells) {
        Set<Integer> seenValues = new HashSet<>();
        for (Cell cell : cells) {
            int value = cell.getValue();
            if (value != 0) { // 0 bedeutet leere Zelle, überspringen
                if (seenValues.contains(value)) {
                    return false; // Duplikat gefunden
                }
                seenValues.add(value);
            }
        }
        return true;
    }

}
