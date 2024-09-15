package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

/**
 * Interface for deduction rules. Deduction rules are used to deduce the value of cells in a sudoku
 * based on the values of other cells in the sudoku.
 * @implNote Strategy pattern
 */
public interface DeductionRule {

    /**
     * Run the deduction rule
     * @param sudoku the sudoku to run the deduction rule on
     * @return true if the deduction rule made a change to the sudoku, false otherwise
     */
    boolean run(Sudoku sudoku);

    /**
     * Get the name of the deduction rule
     * @return the name of the deduction rule
     */
    String getName();
}
