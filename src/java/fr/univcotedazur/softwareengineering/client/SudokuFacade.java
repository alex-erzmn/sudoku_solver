package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.deductionrules.DeductionRule;
import fr.univcotedazur.softwareengineering.deductionrules.DeductionRuleFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import java.io.IOException;
import java.util.List;

/**
 * Facade for the Sudoku application.
 * This class is responsible for creating a Sudoku and solving it.
 * By using the Facade pattern, the client does not need to know the details of the Sudoku creation and solving process.
 * The client only needs to call the createSudoku() method to create a Sudoku and the solveSudoku() method to solve it.
 * @since 12/09/2024
 * @author Alexander Erzmann
 */
public class SudokuFacade {
    private SudokuFactory sudokuFactory;
    private List<DeductionRule> deductionRules;
    private String currentRuleName;

    public SudokuFacade() {
        sudokuFactory = new SudokuFactory();
        deductionRules = new DeductionRuleFactory().createAllDeductionRules();
        currentRuleName = "None";
    }

    public Sudoku createSudoku(SudokuType type) throws IOException {
        return sudokuFactory.createSudoku(type);
    }

    public String step(Sudoku sudoku) {
        currentRuleName = "None";

        for (DeductionRule rule : deductionRules) {
            if (rule.run(sudoku)) {
                currentRuleName = rule.getName();
                return currentRuleName;
            }
        }
        return null;
    }



}
