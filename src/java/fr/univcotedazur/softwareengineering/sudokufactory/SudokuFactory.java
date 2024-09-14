package fr.univcotedazur.softwareengineering.sudokufactory;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import java.io.IOException;

import static fr.univcotedazur.softwareengineering.sudokufactory.sudoku.data.SudokuReader.readSudokuFromFile;

public class SudokuFactory {

    public Sudoku createSudoku(SudokuType sudokuType) throws IOException {
        String filePath = getFilePathForDifficulty(sudokuType);
        return readSudokuFromFile(filePath);
    }

    private String getFilePathForDifficulty(SudokuType sudokuType) {
        return switch (sudokuType) {
            case EASY -> "src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/easySudokus.txt";
            case MEDIUM -> "src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/mediumSudokus.txt";
            case HARD -> "src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/hardSudokus.txt";
            default -> "src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/randomSudokus.txt";
        };
    }
}
