package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.TestSudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DR3Test {

    private DR3 dr3;
    private TestSudokuFactory testSudokuFactory;

    @Test
    void runTest_Successful_Easy() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createEasySudoku();
        dr3 = new DR3();

        //act
        boolean applied = dr3.run(testSudoku);

        //assert
        assertTrue(applied);
    }

    @Test
    void runTest_Successful_Hard() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createHardSudoku();
        dr3 = new DR3();

        //act
        boolean applied = dr3.run(testSudoku);

        //assert
        assertTrue(applied);
    }

    @Test
    void runTest_Failure() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createEmptySudoku();
        dr3 = new DR3();

        //act
        boolean applied = dr3.run(testSudoku);

        //assert
        assertFalse(applied);
    }
}
