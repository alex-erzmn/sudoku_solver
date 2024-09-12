package fr.univcotedazur.softwareengineering.solver;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    private final List<DeductionRule> rules;

    public SudokuSolver() {
        this.rules = new ArrayList<>();
    }

    public void addRule(DeductionRule rule) {
        this.rules.add(rule);
    }

    public boolean step(Sudoku sudoku) {
        boolean progress = false;
        for (DeductionRule rule : rules) {
            if (rule.run(sudoku)) {
                progress = true;
                break; // Nur einen Schritt pro Aufruf
            }
        }
        return progress;
    }

    public boolean isSolved(Sudoku sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.getCell(row, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
