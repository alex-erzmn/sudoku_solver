package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import java.util.List;

public class DR1 implements DeductionRule {

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.getCell(row, col) == 0) {
                    List<Integer> possibleValues = sudoku.calculatePossibleValues(row, col);
                    if (possibleValues.size() == 1) {
                        int value = possibleValues.get(0);
                        sudoku.setCell(row, col, value);
                        wasApplied = true;
                    }
                }
            }
        }
        return wasApplied;
    }
}
