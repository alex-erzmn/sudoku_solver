package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.TestSudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DR2Test {

    private DR2 dr2;
    private TestSudokuFactory testSudokuFactory;

    @Test
    void runTest_Successful() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createEasySudoku();
        dr2 = new DR2();

        //act
        boolean applied = dr2.run(testSudoku);

        //assert
        assertTrue(applied);
    }

    @Test
    void runTest_Failure() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createHardSudoku();
        dr2 = new DR2();

        //act
        boolean applied = dr2.run(testSudoku);

        //assert
        assertFalse(applied);
    }
}
