package fr.univcotedazur.softwareengineering.solver;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

public interface DeductionRule {
    boolean run(Sudoku sudoku);
}
