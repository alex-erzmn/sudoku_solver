package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.solver.DeductionRule;
import fr.univcotedazur.softwareengineering.solver.DR1;
import fr.univcotedazur.softwareengineering.solver.SudokuSolver;
import fr.univcotedazur.softwareengineering.sudokufactory.SudokuFactory;
import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import java.util.HashMap;
import java.util.Map;

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
    private SudokuSolver sudokuSolver;
    private Map<String, DeductionRule> rulesMap;
    private String currentRuleName;

    public SudokuFacade() {
        sudokuFactory = new SudokuFactory();
        sudokuSolver = new SudokuSolver();
        rulesMap = new HashMap<>();
        DeductionRule dr1 = new DR1();
        rulesMap.put("Naked Single", dr1);
        sudokuSolver.addRule(dr1);
        currentRuleName = "None";
    }

    public Sudoku createSudoku() {
        return sudokuFactory.createSudoku();
    }

    public String step(Sudoku sudoku) {
        currentRuleName = "None"; // Zurücksetzen der Regel
        for (Map.Entry<String, DeductionRule> entry : rulesMap.entrySet()) {
            DeductionRule rule = entry.getValue();
            if (rule.run(sudoku)) {
                currentRuleName = entry.getKey();
                return currentRuleName;
            }
        }
        return null; // Kein Fortschritt
    }
}
