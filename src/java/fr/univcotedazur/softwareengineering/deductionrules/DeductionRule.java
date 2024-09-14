package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

public interface DeductionRule {
    boolean run(Sudoku sudoku);
}
