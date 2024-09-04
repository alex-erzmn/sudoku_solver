package softwareengineering;

import softwareengineering.Solver.HiddenSingleRule;
import softwareengineering.Solver.NakedPairRule;
import softwareengineering.Solver.SingleCandidateRule;
import softwareengineering.Solver.SudokuSolver;
import softwareengineering.SudokuFactory.EasySudokuFactory;
import softwareengineering.SudokuFactory.HardSudokuFactory;
import softwareengineering.SudokuFactory.MediumSudokuFactory;
import softwareengineering.SudokuFactory.SudokuFactory;

public class Main {
    public static void main(String[] args) {

        // Ein einfaches Sudoku erstellen
        SudokuFactory easyFactory = new EasySudokuFactory();
        int[][] easySudoku = easyFactory.createSudoku();
        SudokuFactory.printSudoku(easySudoku);

        SudokuSolver solver = new SudokuSolver();
        solver.addRule(new SingleCandidateRule());
        solver.addRule(new HiddenSingleRule());
        solver.addRule(new NakedPairRule());

        if (solver.solve(easySudoku)) {
            System.out.println("Sudoku gelöst:");
            SudokuFactory.printSudoku(easySudoku);
        } else {
            System.out.println("Konnte das Sudoku nicht vollständig lösen.");
        }


/*

        // Ein mittleres Sudoku erstellen
        SudokuFactory mediumFactory = new MediumSudokuFactory();
        int[][] mediumSudoku = mediumFactory.createSudoku();
        SudokuFactory.printSudoku(mediumSudoku);

        // Ein schwieriges Sudoku erstellen
        SudokuFactory hardFactory = new HardSudokuFactory();
        int[][] hardSudoku = hardFactory.createSudoku();
        SudokuFactory.printSudoku(hardSudoku);

*/


        /*
        int[][] sudoku = new int[][] {
                { 0, 9, 0, 0, 0, 0, 1, 1, 0 },
                { 8, 0, 4, 0, 2, 0, 3, 0, 7 },
                { 0, 6, 0, 9, 0, 7, 0, 2, 0 },
                { 0, 0, 5, 0, 3, 0, 1, 0, 0 },
                { 0, 7, 0, 5, 0, 1, 0, 3, 0 },
                { 0, 0, 3, 0, 9, 0, 8, 0, 0 },
                { 0, 2, 0, 8, 0, 5, 0, 6, 0 },
                { 1, 0, 7, 0, 6, 0, 4, 0, 9 },
                { 0, 3, 0, 0, 0, 0, 0, 8, 0 }
        };


        final int[] digits = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // Zahlen auf die geprueft wird. 0 muss extra behandelt werden.

        // Teile das Sudoku in 9 Bloecke (3 mal 3 Felder), also block0, block1, block2, ...
        int[][][] blocks = new int[9][3][3];
        for (int block_index = 0; block_index < 9; block_index++){
            for (int i = 0; i < 9; i++){
                blocks[block_index][i/3][i%3] = sudoku[i/3 + (block_index/3) * 3][(i%3) + (block_index%3) * 3];
                // links geht [i/3][i%3] nacheinander [0][0], [0][1], [0][2], [1][0], [1][1], [1][2], [2][0], [2][1], [2][2] durch
                // rechts tauchen auch die Indices [i/3][i%3] auf, allerdings sind die im gleichen Muster nach rechts in
                // Dreierbloecken oder nach unten in Dreierbloecken verschoben.
            }
        }


        // check sudoku

        // check all rows:
        for (int i = 0; i < 9; i++) {
            int[] row = sudoku[i];
            for (int digit : digits) {
                int digit_occurrence = 0;
                for (int num : row) {
                    if (num == digit) {
                        digit_occurrence++;
                    }
                }
                // Es wurde schon gezaehlt, wie oft eine Zahl in der Zeile vorkam.
                if (digit_occurrence > 1){ // wenn ein digit mehrmals auftritt:
                    System.out.printf("Duplicate number '%d' in row %d.%n", digit, i);
                }

            }
        }

        // check all columns:
        for (int i = 0; i < 9; i++){
            for (int digit : digits) {
                int digit_occurrence = 0;
                // fuer die j-te Zeile:
                for (int j = 0; j < 9; j++) {
                    if (sudoku[j][i] == digit) {
                        digit_occurrence++;
                    }
                }
                // Es wurde schon gezaehlt, wie oft eine Zahl in der Spalte vorkam.
                if (digit_occurrence > 1) { // wenn ein digit mehrmals auftritt:
                    System.out.printf("Duplicate number '%d' in column %d.%n", digit, i);
                }
            }
        }

        // check all blocks:
        for (int block_index = 0; block_index < 9; block_index++){
            for (int digit : digits){
                int digit_occurrence = 0;
                // gehe alle Zeilen und Spalten vom Block: blocks[block_index]
                for (int[] row : blocks[block_index]){
                    for (int number : row){
                        if (number == digit){
                            digit_occurrence++;
                        }
                    }
                }
                // der gesamte Block wurde bearbeitet.
                if (digit_occurrence > 1) { // wenn ein digit mehrmals auftritt:
                    System.out.printf("Duplicate number '%d' in square %d.%n", digit, block_index);
                }
            }
        }
        */
    }
}