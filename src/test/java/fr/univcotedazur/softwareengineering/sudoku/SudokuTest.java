package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.TestSudokuFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SudokuTest {

    private TestSudokuFactory testSudokuFactory;

    @BeforeEach
    public void setUp() {
        testSudokuFactory = TestSudokuFactory.getInstance();
    }

    @Test
    public void testSetValue() {
        //arrange
        Sudoku emptySudoku = testSudokuFactory.createEmptySudoku();
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        Sudoku hardSudoku = testSudokuFactory.createHardSudoku();

        //act
        emptySudoku.setValue(0, 2, 1);
        easySudoku.setValue(0, 2, 1);
        hardSudoku.setValue(0, 2, 1);

        //assert
        //TODO: Check more here!
        assertEquals(1, emptySudoku.getValue(0, 2));
        assertEquals(1, easySudoku.getValue(0, 2));
        assertEquals(1, hardSudoku.getValue(0, 2));
    }
}
