package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.TestSudokuFactory;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DR1Test {

    private DR1 dr1;
    private TestSudokuFactory testSudokuFactory;

    @Test
    public void runTest_Successful() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createEasySudoku();
        dr1 = new DR1();

        //act
        boolean applied = dr1.run(testSudoku);

        //assert
        assertTrue(applied);
    }

    @Test
    public void runTest_Failure() {
        //arrange
        testSudokuFactory = TestSudokuFactory.getInstance();
        Sudoku testSudoku = testSudokuFactory.createHardSudoku();
        dr1 = new DR1();

        //act
        boolean applied = dr1.run(testSudoku);

        //assert
        assertFalse(applied);
    }
}
