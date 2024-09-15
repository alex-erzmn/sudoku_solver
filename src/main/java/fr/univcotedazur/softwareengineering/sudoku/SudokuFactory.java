package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.sudoku.data.SudokuFileLoader;
import java.io.IOException;

/**
 * Factory class to create a Sudoku object from a file. This class is responsible for creating a Sudoku object.
 * @since 12/09/2024
 * @implNote Factory pattern & Singleton pattern
 */
public class SudokuFactory {

    private final SudokuFileLoader sudokuFileLoader;

    private SudokuFactory() {
        sudokuFileLoader = SudokuFileLoader.getInstance();
    }

    private static final class InstanceHolder {
        private static final SudokuFactory instance = new SudokuFactory();
    }

    public static SudokuFactory getInstance() {
        return SudokuFactory.InstanceHolder.instance;
    }


    public Sudoku createSudoku(SudokuType sudokuType) throws IOException {
        String filePath = sudokuFileLoader.getFilePathForDifficulty(sudokuType);
        return sudokuFileLoader.readSudokuFromFile(filePath);
    }

}
