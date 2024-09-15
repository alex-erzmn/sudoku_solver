package fr.univcotedazur.softwareengineering.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuFactory {

    public Sudoku createSudoku(SudokuType sudokuType) throws IOException {
        String filePath = getFilePathForDifficulty(sudokuType);
        return readSudokuFromFile(filePath);
    }

    private String getFilePathForDifficulty(SudokuType sudokuType) {
        return switch (sudokuType) {
            case EASY -> "src/java/fr/univcotedazur/softwareengineering/sudoku/data/easySudokus.txt";
            case MEDIUM -> "src/java/fr/univcotedazur/softwareengineering/sudoku/data/mediumSudokus.txt";
            case HARD -> "src/java/fr/univcotedazur/softwareengineering/sudoku/data/hardSudokus.txt";
            default -> "src/java/fr/univcotedazur/softwareengineering/sudoku/data/randomSudokus.txt";
        };
    }

    private Sudoku readSudokuFromFile(String fileName) throws IOException {
        List<Sudoku> sudokus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Sudoku sudoku = new Sudoku();
            int row = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] numbers = line.split(",");
                for (int col = 0; col < 9; col++) {
                    int value = Integer.parseInt(numbers[col]);
                    sudoku.setCell(row, col, value);
                }

                row++;
                if (row == 9) {
                    sudokus.add(sudoku);
                    sudoku = new Sudoku();
                    row = 0;
                }
            }
        }
        if (sudokus.isEmpty()) {
            throw new IOException("No Sudokus found in the file.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(sudokus.size());

        return sudokus.get(randomIndex);
    }
}
