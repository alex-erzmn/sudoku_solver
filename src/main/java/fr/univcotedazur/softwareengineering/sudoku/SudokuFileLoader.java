package fr.univcotedazur.softwareengineering.sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static fr.univcotedazur.softwareengineering.sudoku.Sudoku.SIZE;

/**
 * Class to load Sudokus from a file. This class is responsible for reading Sudokus from a file. It caches the loaded
 * Sudokus to avoid reading the file multiple times. It also provides a random Sudoku from the loaded Sudokus.
 * @since 12/09/2024
 * @implNote Singleton pattern
 */
public class SudokuFileLoader {

    private final Random random = new Random();

    private final Map<String, List<Sudoku>> sudokuCache = new HashMap<>();
    private static final String EASY_SUDOKU_PATH = "data/easySudokus.txt";
    private static final String MEDIUM_SUDOKU_PATH = "data/mediumSudokus.txt";
    private static final String HARD_SUDOKU_PATH = "data/hardSudokus.txt";
    private static final String RANDOM_SUDOKU_PATH = "data/randomSudokus.txt";

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
        List<Sudoku> cachedSudokus = getCachedSudokus(fileName);
        if (!cachedSudokus.isEmpty()) {
            return getRandomSudoku(cachedSudokus);
        }

        List<Sudoku> sudokus = loadSudokusFromFile(fileName);
        if (sudokus.isEmpty()) {
            throw new IOException("No Sudokus found in the file.");
        }

        sudokuCache.put(fileName, sudokus);
        return getRandomSudoku(sudokus);
    }

    private List<Sudoku> getCachedSudokus(String fileName) {
        return sudokuCache.getOrDefault(fileName, new ArrayList<>());
    }

    private Sudoku getRandomSudoku(List<Sudoku> sudokus) {
        int randomIndex = random.nextInt(sudokus.size());
        return sudokus.get(randomIndex);
    }

    private List<Sudoku> loadSudokusFromFile(String fileName) throws IOException {
        List<Sudoku> sudokus = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("File not found in resources: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Sudoku sudoku = new Sudoku();
            int row = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                parseLineToSudoku(line, row, sudoku);
                row++;

                if (row == SIZE) {
                    sudokus.add(sudoku);
                    sudoku = new Sudoku();
                    row = 0;
                }
            }
        }
        return sudokus;
    }

    private void parseLineToSudoku(String line, int row, Sudoku sudoku) throws IOException {
        String[] numbers = line.split(",");
        if (numbers.length != SIZE) {
            throw new IOException("Invalid line format. Expected " + SIZE + " values but got " + numbers.length);
        }

        for (int col = 0; col < SIZE; col++) {
            int value = parseValue(numbers[col], line);
            sudoku.setValue(row, col, value);
        }
    }

    private int parseValue(String valueStr, String line) throws IOException {
        try {
            int value = Integer.parseInt(valueStr);
            if (value < 0 || value > 9) {
                throw new IOException("Invalid number value in line: " + line);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in line: " + line, e);
        }
    }

}
