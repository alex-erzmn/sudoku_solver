package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Deduction rule that applies the Naked Single strategy. This strategy consists of finding cells that have only one
 * possible value and setting that value to the cell.
 */
public class DR1 implements DeductionRule {

    private static final String NAME = "Naked Single";

    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        // Track cells to be updated
        Set<CellPosition> cellsToUpdate = new HashSet<>();

        // Find cells with exactly one possible value
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.getValue(row, col) == 0) { // Only consider empty cells
                    Set<Integer> possibleValues = sudoku.getPossibleValues(row, col);
                    if (possibleValues.size() == 1) {
                        int value = possibleValues.iterator().next();
                        cellsToUpdate.add(new CellPosition(row, col, value));
                    }
                }
            }
        }

        // Apply updates to the cells that were identified
        for (CellPosition cellPosition : cellsToUpdate) {
            sudoku.setValue(cellPosition.row, cellPosition.col, cellPosition.value);
            wasApplied = true;
        }

        return wasApplied;
    }

    private static class CellPosition {
        final int row;
        final int col;
        final int value;

        CellPosition(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }
}
