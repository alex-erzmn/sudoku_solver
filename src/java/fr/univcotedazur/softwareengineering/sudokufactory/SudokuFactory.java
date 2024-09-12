package fr.univcotedazur.softwareengineering.sudokufactory;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import static fr.univcotedazur.softwareengineering.sudokufactory.sudoku.data.SudokuReader.readSudokuFromFile;

public class SudokuFactory {

    public Sudoku createSudoku() {
        String filePath = "src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/randomSudokus.txt";

        // Zufälliges Sudoku aus der Datei lesen
        return readSudokuFromFile(filePath);
    }
}
