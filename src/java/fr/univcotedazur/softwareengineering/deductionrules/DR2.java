package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import java.util.Set;

public class DR2 implements DeductionRule {

    private static final String NAME = "Hidden Single";

    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        for (int row = 0; row < 9; row++) {
            for (int value = 1; value <= 9; value++) {
                if (findAndPlaceHiddenSingle(sudoku, row, -1, value)) {
                    wasApplied = true;
                }
            }
        }

        for (int col = 0; col < 9; col++) {
            for (int value = 1; value <= 9; value++) {
                if (findAndPlaceHiddenSingle(sudoku, -1, col, value)) {
                    wasApplied = true;
                }
            }
        }

        for (int box = 0; box < 9; box++) {
            for (int value = 1; value <= 9; value++) {
                if (findAndPlaceHiddenSingleInBox(sudoku, box, value)) {
                    wasApplied = true;
                }
            }
        }

        return wasApplied;
    }

    private boolean findAndPlaceHiddenSingle(Sudoku sudoku, int row, int col, int value) {
        boolean wasApplied = false;
        int count = 0;
        int targetIndex = -1;

        for (int i = 0; i < 9; i++) {
            if (row != -1) {
                Set<Integer> possibleValues = sudoku.getPossibleValues(row, i);
                if (possibleValues.contains(value)) {
                    count++;
                    targetIndex = i;
                }
            } else if (col != -1) {
                Set<Integer> possibleValues = sudoku.getPossibleValues(i, col);
                if (possibleValues.contains(value)) {
                    count++;
                    targetIndex = i;
                }
            }
        }

        if (count == 1) {
            if (row != -1) {

                if (sudoku.getCell(row, targetIndex) == 0) {
                    sudoku.setCell(row, targetIndex, value);
                    wasApplied = true;
                }
            } else if (col != -1 && sudoku.getCell(targetIndex, col) == 0) {
                    sudoku.setCell(targetIndex, col, value);
                    wasApplied = true;
                }

        }

        return wasApplied;
    }

    private boolean findAndPlaceHiddenSingleInBox(Sudoku sudoku, int boxIndex, int value) {
        boolean wasApplied = false;
        int startRow = (boxIndex / 3) * 3;
        int startCol = (boxIndex % 3) * 3;
        int count = 0;
        int targetRow = -1;
        int targetCol = -1;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                Set<Integer> possibleValues = sudoku.getPossibleValues(r, c);
                if (possibleValues.contains(value)) {
                    count++;
                    targetRow = r;
                    targetCol = c;
                }
            }
        }

        if (count == 1 && sudoku.getCell(targetRow, targetCol) == 0) {
                sudoku.setCell(targetRow, targetCol, value);
                wasApplied = true;
            }


        return wasApplied;
    }
}
