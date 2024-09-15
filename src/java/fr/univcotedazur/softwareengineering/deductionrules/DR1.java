package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import java.util.Set;

public class DR1 implements DeductionRule {

    private static final String NAME = "Naked Single";

    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        boolean wasApplied = false;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.getCell(row, col) == 0) {
                    Set<Integer> possibleValues = sudoku.getPossibleValues(row, col);
                    if (possibleValues.size() == 1) {
                        int value = possibleValues.iterator().next();
                        sudoku.setCell(row, col, value);
                        wasApplied = true;
                    }
                }
            }
        }
        return wasApplied;
    }
}
