package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

public interface DeductionRule {
    boolean run(Sudoku sudoku);
    String getName();
}
