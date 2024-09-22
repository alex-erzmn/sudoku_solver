package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import java.util.HashSet;
import java.util.Set;

public class DR3 implements DeductionRule {

    private static final String NAME = "Box-Line Reduction";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        for (int boxIndex = 0; boxIndex < 9; boxIndex++) {
            int boxStartRow = (boxIndex / 3) * 3;
            int boxStartCol = (boxIndex % 3) * 3;

            for (int num = 1; num <= 9; num++) {
                wasApplied |= applyBoxLineReduction(sudoku, boxStartRow, boxStartCol, num);
            }
        }

        return wasApplied;
    }

    private boolean applyBoxLineReduction(Sudoku sudoku, int boxStartRow, int boxStartCol, int num) {
        Set<Integer> rowsWithNum = new HashSet<>();
        Set<Integer> colsWithNum = new HashSet<>();

        collectRowsAndColsWithNum(sudoku, boxStartRow, boxStartCol, num, rowsWithNum, colsWithNum);

        boolean wasApplied = false;

        if (rowsWithNum.size() == 1) {
            int rowToClear = rowsWithNum.iterator().next();
            wasApplied |= clearRowOutsideBox(sudoku, boxStartCol, rowToClear, num);
        }

        if (colsWithNum.size() == 1) {
            int colToClear = colsWithNum.iterator().next();
            wasApplied |= clearColOutsideBox(sudoku, boxStartRow, colToClear, num);
        }

        return wasApplied;
    }

    private void collectRowsAndColsWithNum(Sudoku sudoku, int boxStartRow, int boxStartCol, int num, Set<Integer> rowsWithNum, Set<Integer> colsWithNum) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int row = boxStartRow + i;
                int col = boxStartCol + j;
                if (sudoku.getValue(row, col) == 0 && sudoku.getPossibleValues(row, col).contains(num)) {
                    rowsWithNum.add(row);
                    colsWithNum.add(col);
                }
            }
        }
    }

    private boolean clearRowOutsideBox(Sudoku sudoku, int boxStartCol, int row, int num) {
        boolean wasCleared = false;
        for (int col = 0; col < 9; col++) {
            if ((col < boxStartCol || col >= boxStartCol + 3) && sudoku.getPossibleValues(row, col).contains(num)) {
                    sudoku.removePossibleValue(row, col, num);
                    wasCleared = true;
                }
        }
        return wasCleared;
    }

    private boolean clearColOutsideBox(Sudoku sudoku, int boxStartRow, int col, int num) {
        boolean wasCleared = false;
        for (int row = 0; row < 9; row++) {
            if ((row < boxStartRow || row >= boxStartRow + 3) && sudoku.getPossibleValues(row, col).contains(num)) {
                    sudoku.removePossibleValue(row, col, num);
                    wasCleared = true;
                }
        }
        return wasCleared;
    }


}
