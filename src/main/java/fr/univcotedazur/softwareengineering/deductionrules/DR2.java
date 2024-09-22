package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import java.util.Set;

/**
 * Deduction rule that applies the Hidden Single strategy. This strategy consists of finding cells that have only one
 * possible value. In comparison to the Naked Single strategy, the possible value is not the only possible value
 * in the cell, but it is the only possible value in the row, column or box.
 */
public class DR2 implements DeductionRule {

    private static final String NAME = "Hidden Single";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        for (int i = 0; i < 9; i++) {
            wasApplied |= processRow(sudoku, i);
            wasApplied |= processColumn(sudoku, i);
            wasApplied |= processBox(sudoku, i);
        }
        return wasApplied;
    }

    private boolean processRow(Sudoku sudoku, int row) {
        boolean wasApplied = false;
        for (int value = 1; value <= 9; value++) {
            wasApplied |= findAndPlaceHiddenSingleInRow(sudoku, row, value);
        }
        return wasApplied;
    }

    private boolean processColumn(Sudoku sudoku, int col) {
        boolean wasApplied = false;
        for (int value = 1; value <= 9; value++) {
            wasApplied |= findAndPlaceHiddenSingleInColumn(sudoku, col, value);
        }
        return wasApplied;
    }

    private boolean processBox(Sudoku sudoku, int box) {
        boolean wasApplied = false;
        for (int value = 1; value <= 9; value++) {
            wasApplied |= findAndPlaceHiddenSingleInBox(sudoku, box, value);
        }
        return wasApplied;
    }

    private boolean findAndPlaceHiddenSingleInRow(Sudoku sudoku, int row, int value) {
        int count = 0;
        int targetIndex = -1;

        for (int col = 0; col < 9; col++) {
            if (isValuePossibleInCell(sudoku, row, col, value)) {
                count++;
                targetIndex = col;
            }
        }

        if (count == 1 && sudoku.getValue(row, targetIndex) == 0) {
            sudoku.setValue(row, targetIndex, value);
            return true;
        }

        return false;
    }

    private boolean findAndPlaceHiddenSingleInColumn(Sudoku sudoku, int col, int value) {
        int count = 0;
        int targetIndex = -1;

        for (int row = 0; row < 9; row++) {
            if (isValuePossibleInCell(sudoku, row, col, value)) {
                count++;
                targetIndex = row;
            }
        }

        if (count == 1 && sudoku.getValue(targetIndex, col) == 0) {
            sudoku.setValue(targetIndex, col, value);
            return true;
        }

        return false;
    }


    private boolean findAndPlaceHiddenSingleInBox(Sudoku sudoku, int boxIndex, int value) {
        int count = 0;
        int targetRow = -1;
        int targetCol = -1;

        int startRow = (boxIndex / 3) * 3;
        int startCol = (boxIndex % 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (isValuePossibleInCell(sudoku, r, c, value)) {
                    count++;
                    targetRow = r;
                    targetCol = c;
                }
            }
        }

        if (count == 1 && sudoku.getValue(targetRow, targetCol) == 0) {
            sudoku.setValue(targetRow, targetCol, value);
            return true;
        }
        return false;
    }

    private boolean isValuePossibleInCell(Sudoku sudoku, int row, int col, int value) {
        Set<Integer> possibleValues = sudoku.getPossibleValues(row, col);
        return possibleValues.contains(value);
    }
}
