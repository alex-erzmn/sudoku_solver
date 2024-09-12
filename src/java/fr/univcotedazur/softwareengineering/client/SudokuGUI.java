package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame implements SudokuObserver {
    private static final int SIZE = 9;
    private JButton[][] cells;
    private SudokuFacade facade;
    private Sudoku sudoku;
    private JLabel ruleLabel;

    public SudokuGUI() {
        facade = new SudokuFacade(); // Initialisiere die Facade
        setTitle("Sudoku Solver");
        setSize(700, 800); // Erhöhe die Größe für bessere Lesbarkeit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sudoku-Board-Panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Rahmen um das Sudoku-Board

        cells = new JButton[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JButton("");
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                cells[row][col].setFocusPainted(false);
                cells[row][col].setBackground(Color.WHITE);
                cells[row][col].setOpaque(true);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                cells[row][col].addActionListener(new CellButtonListener(row, col));
                boardPanel.add(cells[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Control-Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Abstände zwischen den Komponenten

        JButton createSudokuButton = new JButton("Create Sudoku");
        JButton stepButton = new JButton("Next Step");
        JButton solveButton = new JButton("Solve");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        controlPanel.add(createSudokuButton, gbc);

        gbc.gridy = 1;
        controlPanel.add(stepButton, gbc);

        gbc.gridy = 2;
        controlPanel.add(solveButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        // Status-Label zur Anzeige der aktuellen Regel
        ruleLabel = new JLabel("Current Rule: None");
        ruleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ruleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(ruleLabel, BorderLayout.NORTH);

        // Button-ActionListener
        createSudokuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku = facade.createSudoku();
                sudoku.addObserver(SudokuGUI.this);
                updateSudoku();
                ruleLabel.setText("Current Rule: None");
            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruleName = facade.step(sudoku);
                if (ruleName != null) {
                    ruleLabel.setText("Current Rule: " + ruleName);
                    if (isSolved(sudoku)) {
                        JOptionPane.showMessageDialog(null, "Sudoku gelöst!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Das Sudoku kann nicht weiter gelöst werden. Please fill any cell.");
                }
            }
        });

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean solved = false;
                while (!solved) {
                    String ruleName = facade.step(sudoku);
                    if (ruleName != null) {
                        ruleLabel.setText("Current Rule: " + ruleName);
                        if (isSolved(sudoku)) {
                            JOptionPane.showMessageDialog(null, "Sudoku gelöst!");
                            solved = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Keine weiteren Fortschritte möglich.");
                        break;
                    }
                }
            }
        });
    }

    // Methode zur Aktualisierung der GUI mit dem aktuellen Sudoku-Board
    @Override
    public void updateSudoku() {
        if (sudoku == null) return;

        int[][] board = sudoku.getSudokuGrid();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                if (value == 0) {
                    cells[row][col].setText("");
                    cells[row][col].setBackground(Color.WHITE);
                } else {
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private boolean isSolved(Sudoku sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku.getCell(row, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private class CellButtonListener implements ActionListener {
        private final int row;
        private final int col;

        public CellButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(null,
                    "Geben Sie den Wert (1-9) für die Zelle (" + (row + 1) + ", " + (col + 1) + ") ein:",
                    "Manuelle Eingabe", JOptionPane.PLAIN_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    int value = Integer.parseInt(input);
                    if (value >= 1 && value <= 9) {
                        sudoku.setCell(row, col, value);
                        updateSudoku();
                        String ruleName = facade.step(sudoku);
                        if (ruleName != null) {
                            ruleLabel.setText("Current Rule: " + ruleName);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ungültiger Wert. Der Wert muss zwischen 1 und 9 liegen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}