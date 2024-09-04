package softwareengineering;

import softwareengineering.Solver.HiddenSingleRule;
import softwareengineering.Solver.NakedPairRule;
import softwareengineering.Solver.SingleCandidateRule;
import softwareengineering.Solver.SudokuSolver;
import softwareengineering.SudokuFactory.EasySudokuFactory;
import softwareengineering.SudokuFactory.HardSudokuFactory;
import softwareengineering.SudokuFactory.MediumSudokuFactory;
import softwareengineering.SudokuFactory.SudokuFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private static final int SIZE = 9;
    private JButton[][] cells;
    private int[][] board;
    private SudokuFactory factory;

    public SudokuGUI() {
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sudoku-Board-Panel
        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        cells = new JButton[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JButton("");
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[row][col].setFocusPainted(false);
                boardPanel.add(cells[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Control-Panel mit den Buttons
        JPanel controlPanel = new JPanel();
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton solveButton = new JButton("Solve");

        controlPanel.add(easyButton);
        controlPanel.add(mediumButton);
        controlPanel.add(hardButton);
        controlPanel.add(solveButton);
        add(controlPanel, BorderLayout.SOUTH);

        // ActionListener für die Schwierigkeits-Buttons
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factory = new EasySudokuFactory();
                board = factory.createSudoku();
                updateBoard();
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factory = new MediumSudokuFactory();
                board = factory.createSudoku();
                updateBoard();
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factory = new HardSudokuFactory();
                board = factory.createSudoku();
                updateBoard();
            }
        });

        // ActionListener für den Solve-Button
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var solver = new SudokuSolver();
                solver.addRule(new SingleCandidateRule());
                solver.addRule(new HiddenSingleRule());
                solver.addRule(new NakedPairRule());

                if (solver.solve(board)) {
                    updateBoard();
                    JOptionPane.showMessageDialog(null, "Sudoku gelöst!");
                } else {
                    JOptionPane.showMessageDialog(null, "Konnte das Sudoku nicht vollständig lösen.");
                }
            }
        });
    }

    // Methode zur Aktualisierung der GUI mit dem aktuellen Sudoku-Board
    private void updateBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    cells[row][col].setText("");
                } else {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                }
            }
        }
    }
}
