package fr.univcotedazur.softwareengineering.sudoku.data;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SudokuFileLoader {

    private static final int SUDOKU_SIZE = 9;
    private final Map<String, List<Sudoku>> sudokuCache = new HashMap<>();
    private static final String EASY_SUDOKU_PATH =
            "src/main/java/fr/univcotedazur/softwareengineering/sudoku/data/easySudokus.txt";
    private static final String MEDIUM_SUDOKU_PATH =
            "src/main/java/fr/univcotedazur/softwareengineering/sudoku/data/mediumSudokus.txt";
    private static final String HARD_SUDOKU_PATH =
            "src/main/java/fr/univcotedazur/softwareengineering/sudoku/data/hardSudokus.txt";
    private static final String RANDOM_SUDOKU_PATH =
            "src/main/java/fr/univcotedazur/softwareengineering/sudoku/data/randomSudokus.txt";

    private SudokuFileLoader() {
    }

    private static final class InstanceHolder {
        private static final SudokuFileLoader instance = new SudokuFileLoader();
    }

    public static SudokuFileLoader getInstance() {
        return InstanceHolder.instance;
    }

    public String getFilePathForDifficulty(SudokuType sudokuType) {
        return switch (sudokuType) {
            case EASY -> EASY_SUDOKU_PATH;
            case MEDIUM -> MEDIUM_SUDOKU_PATH;
            case HARD -> HARD_SUDOKU_PATH;
            default -> RANDOM_SUDOKU_PATH;
        };
    }


    public Sudoku readSudokuFromFile(String fileName) throws IOException {

        List<Sudoku> cachedSudokus = sudokuCache.get(fileName);
        if (cachedSudokus != null && !cachedSudokus.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(cachedSudokus.size());
            return cachedSudokus.get(randomIndex);
        }

        List<Sudoku> sudokus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Sudoku sudoku = new Sudoku();
            int row = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] numbers = line.split(",");
                if (numbers.length != SUDOKU_SIZE) {
                    throw new IOException("Invalid line format. Expected " + SUDOKU_SIZE + " values but got " + numbers.length);
                }

                for (int col = 0; col < SUDOKU_SIZE; col++) {
                    int value;
                    try {
                        value = Integer.parseInt(numbers[col]);
                    } catch (NumberFormatException e) {
                        throw new IOException("Invalid number format in line: " + line, e);
                    }
                    if (value < 0 || value > 9) {
                        throw new IOException("Invalid number value in line: " + line);
                    }
                    sudoku.setCell(row, col, value);
                }

                row++;
                if (row == SUDOKU_SIZE) {
                    sudokus.add(sudoku);
                    sudoku = new Sudoku();
                    row = 0;
                }
            }
        }

        if (sudokus.isEmpty()) {
            throw new IOException("No Sudokus found in the file.");
        }

        sudokuCache.put(fileName, sudokus);

        Random random = new Random();
        int randomIndex = random.nextInt(sudokus.size());
        return sudokus.get(randomIndex);
    }
}
