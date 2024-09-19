package fr.univcotedazur.softwareengineering.sudoku;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the SudokuFileLoader class. This class uses the data from the testdata.txt file to test the
 * readSudokuFromFile method.
 * @see SudokuFileLoader
 */
public class SudokuFileLoaderTest {

    @Test
    public void getFilePathForDifficultyTest() {
        // Arrange
        SudokuFileLoader sudokuFileLoader = SudokuFileLoader.getInstance();

        // Act & Assert
        assertEquals("data/easySudokus.txt", sudokuFileLoader.getFilePathForDifficulty(SudokuType.EASY), "File path for EASY should be correct");
        assertEquals("data/mediumSudokus.txt", sudokuFileLoader.getFilePathForDifficulty(SudokuType.MEDIUM), "File path for MEDIUM should be correct");
        assertEquals("data/hardSudokus.txt", sudokuFileLoader.getFilePathForDifficulty(SudokuType.HARD), "File path for HARD should be correct");
        assertEquals("data/randomSudokus.txt", sudokuFileLoader.getFilePathForDifficulty(SudokuType.RANDOM), "File path for RANDOM should be correct");
    }

    @Test
    public void readSudokuFromFileTest_Successful() throws IOException {
        // arrange
        SudokuFileLoader sudokuFileLoader = SudokuFileLoader.getInstance();
        String filePath = "testdata/testdata_correct.txt";

        // act
        Sudoku sudoku = sudokuFileLoader.readSudokuFromFile(filePath);

        // assert
        // Row 1
        assertEquals(0, sudoku.getValue(0, 0));
        assertEquals(7, sudoku.getValue(0, 1));
        assertEquals(0, sudoku.getValue(0, 2));
        assertEquals(0, sudoku.getValue(0, 3));
        assertEquals(0, sudoku.getValue(0, 4));
        assertEquals(0, sudoku.getValue(0, 5));
        assertEquals(0, sudoku.getValue(0, 6));
        assertEquals(4, sudoku.getValue(0, 7));
        assertEquals(3, sudoku.getValue(0, 8));

        // Row 2
        assertEquals(0, sudoku.getValue(1, 0));
        assertEquals(4, sudoku.getValue(1, 1));
        assertEquals(0, sudoku.getValue(1, 2));
        assertEquals(0, sudoku.getValue(1, 3));
        assertEquals(0, sudoku.getValue(1, 4));
        assertEquals(9, sudoku.getValue(1, 5));
        assertEquals(6, sudoku.getValue(1, 6));
        assertEquals(1, sudoku.getValue(1, 7));
        assertEquals(0, sudoku.getValue(1, 8));

        // Row 3
        assertEquals(8, sudoku.getValue(2, 0));
        assertEquals(0, sudoku.getValue(2, 1));
        assertEquals(0, sudoku.getValue(2, 2));
        assertEquals(6, sudoku.getValue(2, 3));
        assertEquals(3, sudoku.getValue(2, 4));
        assertEquals(4, sudoku.getValue(2, 5));
        assertEquals(9, sudoku.getValue(2, 6));
        assertEquals(0, sudoku.getValue(2, 7));
        assertEquals(0, sudoku.getValue(2, 8));

        // Row 4
        assertEquals(0, sudoku.getValue(3, 0));
        assertEquals(9, sudoku.getValue(3, 1));
        assertEquals(4, sudoku.getValue(3, 2));
        assertEquals(0, sudoku.getValue(3, 3));
        assertEquals(5, sudoku.getValue(3, 4));
        assertEquals(2, sudoku.getValue(3, 5));
        assertEquals(0, sudoku.getValue(3, 6));
        assertEquals(0, sudoku.getValue(3, 7));
        assertEquals(0, sudoku.getValue(3, 8));

        // Row 5
        assertEquals(3, sudoku.getValue(4, 0));
        assertEquals(5, sudoku.getValue(4, 1));
        assertEquals(8, sudoku.getValue(4, 2));
        assertEquals(4, sudoku.getValue(4, 3));
        assertEquals(6, sudoku.getValue(4, 4));
        assertEquals(0, sudoku.getValue(4, 5));
        assertEquals(0, sudoku.getValue(4, 6));
        assertEquals(2, sudoku.getValue(4, 7));
        assertEquals(0, sudoku.getValue(4, 8));

        // Row 6
        assertEquals(0, sudoku.getValue(5, 0));
        assertEquals(0, sudoku.getValue(5, 1));
        assertEquals(0, sudoku.getValue(5, 2));
        assertEquals(8, sudoku.getValue(5, 3));
        assertEquals(0, sudoku.getValue(5, 4));
        assertEquals(0, sudoku.getValue(5, 5));
        assertEquals(5, sudoku.getValue(5, 6));
        assertEquals(3, sudoku.getValue(5, 7));
        assertEquals(0, sudoku.getValue(5, 8));

        // Row 7
        assertEquals(0, sudoku.getValue(6, 0));
        assertEquals(8, sudoku.getValue(6, 1));
        assertEquals(0, sudoku.getValue(6, 2));
        assertEquals(0, sudoku.getValue(6, 3));
        assertEquals(7, sudoku.getValue(6, 4));
        assertEquals(0, sudoku.getValue(6, 5));
        assertEquals(0, sudoku.getValue(6, 6));
        assertEquals(9, sudoku.getValue(6, 7));
        assertEquals(1, sudoku.getValue(6, 8));

        // Row 8
        assertEquals(9, sudoku.getValue(7, 0));
        assertEquals(0, sudoku.getValue(7, 1));
        assertEquals(2, sudoku.getValue(7, 2));
        assertEquals(1, sudoku.getValue(7, 3));
        assertEquals(0, sudoku.getValue(7, 4));
        assertEquals(0, sudoku.getValue(7, 5));
        assertEquals(0, sudoku.getValue(7, 6));
        assertEquals(0, sudoku.getValue(7, 7));
        assertEquals(5, sudoku.getValue(7, 8));

        // Row 9
        assertEquals(0, sudoku.getValue(8, 0));
        assertEquals(0, sudoku.getValue(8, 1));
        assertEquals(7, sudoku.getValue(8, 2));
        assertEquals(0, sudoku.getValue(8, 3));
        assertEquals(4, sudoku.getValue(8, 4));
        assertEquals(0, sudoku.getValue(8, 5));
        assertEquals(8, sudoku.getValue(8, 6));
        assertEquals(0, sudoku.getValue(8, 7));
        assertEquals(2, sudoku.getValue(8, 8));
    }

    @Test
    public void readSudokuFromFileTest_FailureInvalidLineFormat() {
        // arrange
        SudokuFileLoader sudokuFileLoader = SudokuFileLoader.getInstance();
        String filePath = "testdata/testdata_invalidLineFormat.txt";

        // act & assert
        IOException exception = assertThrows(IOException.class, () -> {
            sudokuFileLoader.readSudokuFromFile(filePath);
        });

        // assert the exception message
        assertEquals("Invalid line format. Expected 9 values but got 10", exception.getMessage());
    }

    @Test
    public void readSudokuFromFileTest_FailureInvalidNumberFormat() {
        // arrange
        SudokuFileLoader sudokuFileLoader = SudokuFileLoader.getInstance();
        String filePath = "testdata/testdata_invalidNumberFormat.txt";

        // act & assert
        IOException exception = assertThrows(IOException.class, () -> {
            sudokuFileLoader.readSudokuFromFile(filePath);
        });

        // assert the exception message
        assertEquals("Invalid number value in line: 10,7,0,0,0,0,0,4,3", exception.getMessage());
    }

    @Test
    public void readSudokuFromFileTest_FailureEmptyFile() {
        // arrange
        SudokuFileLoader sudokuFileLoader = SudokuFileLoader.getInstance();
        String filePath = "testdata/testdata_empty.txt";

        // act & assert
        IOException exception = assertThrows(IOException.class, () -> {
            sudokuFileLoader.readSudokuFromFile(filePath);
        });

        // assert the exception message
        assertEquals("No Sudokus found in the file.", exception.getMessage());
    }

}
