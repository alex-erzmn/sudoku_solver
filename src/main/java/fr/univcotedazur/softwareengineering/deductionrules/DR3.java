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
                Set<Integer> rowsWithNum = new HashSet<>();
                Set<Integer> colsWithNum = new HashSet<>();

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

                if (rowsWithNum.size() == 1) {
                    int rowToClear = rowsWithNum.iterator().next();

                    for (int col = 0; col < 9; col++) {
                        if (col < boxStartCol || col >= boxStartCol + 3) {
                            if (sudoku.getPossibleValues(rowToClear, col).contains(num)) {
                                sudoku.removePossibleValue(rowToClear, col, num);
                                wasApplied = true;
                            }
                        }
                    }
                }

                if (colsWithNum.size() == 1) {
                    int colToClear = colsWithNum.iterator().next();

                    for (int row = 0; row < 9; row++) {
                        if (row < boxStartRow || row >= boxStartRow + 3) {
                            if (sudoku.getPossibleValues(row, colToClear).contains(num)) {
                                sudoku.removePossibleValue(row, colToClear, num);
                                wasApplied = true;
                            }
                        }
                    }
                }
            }
        }

        return wasApplied;
    }

}
