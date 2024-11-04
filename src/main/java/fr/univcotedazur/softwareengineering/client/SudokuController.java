package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.deductionrules.DeductionRule;
import fr.univcotedazur.softwareengineering.deductionrules.DeductionRuleFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.SudokuFileLoader;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import lombok.Getter;

import java.io.File;
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
    private final SudokuFileLoader sudokuFileLoader;
    private final DeductionRuleFactory ruleFactory;
    private List<DeductionRule> deductionRules;
    @Getter
    private Sudoku sudoku;
    @Getter
    private String currentRuleName;

    public SudokuController() {
        sudokuFactory = SudokuFactory.getInstance();
        sudokuFileLoader = SudokuFileLoader.getInstance();
        ruleFactory = DeductionRuleFactory.getInstance();
        currentRuleName = "None";
    }

    public Sudoku createSudoku(SudokuType type) throws IOException {
        sudoku = sudokuFactory.createSudoku(type);
        sudoku.initializePossibleValues();
        return sudoku;
    }

    public Sudoku loadSudokuFromFile(File file) throws IOException {
        sudoku = sudokuFileLoader.readSudokuFromFile(file.getAbsolutePath());
        sudoku.initializePossibleValues();
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
        deductionRules = ruleFactory.createAllDeductionRules();
        List<String> ruleNames = new ArrayList<>();
        for (DeductionRule rule : deductionRules) {
            ruleNames.add(rule.getName());
        }
        return ruleNames;
    }

    public void setCell(int row, int col, int value) {
        if (sudoku != null) {
            sudoku.setValue(row, col, value);
        }
    }
}
