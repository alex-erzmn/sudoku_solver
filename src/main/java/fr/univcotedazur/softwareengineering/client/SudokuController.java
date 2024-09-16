package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.deductionrules.DeductionRule;
import fr.univcotedazur.softwareengineering.deductionrules.DeductionRuleFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade for the Sudoku application.
 * This class is responsible for creating a Sudoku and solving it.
 * By using the Facade pattern, the client does not need to know the details of the Sudoku creation and solving process.
 * The client only needs to call the createSudoku() method to create a Sudoku and the solveSudoku() method to solve it.
 * @since 12/09/2024
 * @implNote Facade pattern
 */
public class SudokuController {
    private final SudokuFactory sudokuFactory;
    private DeductionRuleFactory ruleFactory;
    private final List<DeductionRule> deductionRules;
    private Sudoku sudoku;
    private String currentRuleName;

    public SudokuController() {
        sudokuFactory = SudokuFactory.getInstance();
        ruleFactory = DeductionRuleFactory.getInstance();
        deductionRules = ruleFactory.createAllDeductionRules();
        currentRuleName = "None";
    }

    public Sudoku createSudoku(SudokuType type) throws IOException {
        SudokuChecker sudokuChecker = new SudokuChecker();
        sudoku = sudokuFactory.createSudoku(type);
        sudoku.addObserver(sudokuChecker);
        return sudoku;
    }

    public String solve() {
        currentRuleName = "None";

        if (sudoku == null) return null;

        for (DeductionRule rule : deductionRules) {
            if (rule.run(sudoku)) {
                currentRuleName = rule.getName();
                return currentRuleName;
            }
        }
        return null;
    }

    public List<String> getDeductionRules() {
        List<String> ruleNames = new ArrayList<>();
        for (DeductionRule rule : deductionRules) {
            ruleNames.add(rule.getName());
        }
        return ruleNames;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setCell(int row, int col, int value) {
        if (sudoku != null) {
            sudoku.setValue(row, col, value);
        }
    }
}
