package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.TestSudokuFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SudokuTest {

    private TestSudokuFactory testSudokuFactory;

    @BeforeEach
    public void setUp() {
        testSudokuFactory = TestSudokuFactory.getInstance();
    }

    @Test
    void getValueTest() {
        //arrange
        Sudoku emptySudoku = testSudokuFactory.createEmptySudoku();
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        Sudoku hardSudoku = testSudokuFactory.createHardSudoku();

        //act
        int value1 = emptySudoku.getValue(0, 0);
        int value2 = easySudoku.getValue(0, 1);
        int value3 = hardSudoku.getValue(8, 6);

        //assert
        assertEquals(0, value1);
        assertEquals(3, value2);
        assertEquals(4, value3);
    }

    @Test
    void setValueTest() {
        //arrange
        Sudoku emptySudoku = testSudokuFactory.createEmptySudoku();
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        Sudoku hardSudoku = testSudokuFactory.createHardSudoku();

        //act
        emptySudoku.setValue(0, 2, 1);
        easySudoku.setValue(0, 2, 1);
        hardSudoku.setValue(0, 2, 1);

        // assert
        assertEquals(1, emptySudoku.getValue(0, 2));
        assertEquals(1, easySudoku.getValue(0, 2));
        assertEquals(1, hardSudoku.getValue(0, 2));
    }

    @Test
    void getPossibleValuesTest() {
        //arrange
        Sudoku emptySudoku = testSudokuFactory.createEmptySudoku();
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        Sudoku hardSudoku = testSudokuFactory.createHardSudoku();

        //act
        Set<Integer> possibleValues1 = emptySudoku.getPossibleValues(0, 0);
        Set<Integer> possibleValues2 = easySudoku.getPossibleValues(0, 0);
        Set<Integer> possibleValues3 = hardSudoku.getPossibleValues(0, 0);

        //assert
        Set<Integer> expectedValues1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Set<Integer> expectedValues2 = new HashSet<>();
        Set<Integer> expectedValues3 = new HashSet<>(Arrays.asList(1, 2, 4, 5, 6, 9));

        assertEquals(expectedValues1, possibleValues1);
        assertEquals(expectedValues2, possibleValues2);
        assertEquals(expectedValues3, possibleValues3);
    }

    @Test
    void initializePossibleValuesTest() {
        //arrange
        Sudoku easySudoku = testSudokuFactory.createEasySudokuWithoutPossibleValues();

        //act
        easySudoku.initializePossibleValues();

        //assert
        Set<Integer> expectedValuesForCell00 = new HashSet<>();
        Set<Integer> expectedValuesForCell01 = new HashSet<>(Arrays.asList(1, 2, 4));
        Set<Integer> expectedValuesForCell02 = new HashSet<>(Arrays.asList(2, 6));
        Set<Integer> expectedValuesForCell12 = new HashSet<>(Arrays.asList(2, 4, 7));

        assertEquals(expectedValuesForCell00, easySudoku.getPossibleValues(0, 0));
        assertEquals(expectedValuesForCell01, easySudoku.getPossibleValues(0, 2));
        assertEquals(expectedValuesForCell02, easySudoku.getPossibleValues(0, 3));
        assertEquals(expectedValuesForCell12, easySudoku.getPossibleValues(1, 2));
        // ...
    }

    @Test
    void addPossibleValuesTest() {
        // arrange
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        Set<Integer> initialPossibleValues = new HashSet<>(easySudoku.getPossibleValues(0, 2));

        // act
        easySudoku.addPossibleValue(0, 2, 9);
        Set<Integer> updatedPossibleValues = easySudoku.getPossibleValues(0, 2);

        // assert
        assertEquals(initialPossibleValues.size() + 1, updatedPossibleValues.size());
        assertTrue(updatedPossibleValues.contains(1));
    }

    @Test
    void removePossibleValueTest() {
        // arrange
        Sudoku easySudoku = testSudokuFactory.createEasySudoku();
        easySudoku.addPossibleValue(0, 2, 1); // Stelle sicher, dass der Wert hinzugefügt wurde
        Set<Integer> initialPossibleValues = new HashSet<>(easySudoku.getPossibleValues(0, 2));

        // act
        easySudoku.removePossibleValue(0, 2, 1);
        Set<Integer> updatedPossibleValues = easySudoku.getPossibleValues(0, 2);

        // assert
        assertEquals(initialPossibleValues.size() - 1, updatedPossibleValues.size());
        assertFalse(updatedPossibleValues.contains(1));
    }

    @Test
    void isSolvedTest() {
        // arrange
        Sudoku emptySudoku = testSudokuFactory.createEmptySudoku();
        Sudoku hardSudoku = testSudokuFactory.createHardSudoku();
        Sudoku solvedSudoku = testSudokuFactory.createSolvedSudoku();

        // act
        boolean emptySudokuSolved = emptySudoku.isSolved();
        boolean hardSudokuSolved = hardSudoku.isSolved();
        boolean solvedSudokuSolved = solvedSudoku.isSolved();

        // assert
        assertFalse(emptySudokuSolved);
        assertFalse(hardSudokuSolved);
        assertTrue(solvedSudokuSolved);
    }
}
