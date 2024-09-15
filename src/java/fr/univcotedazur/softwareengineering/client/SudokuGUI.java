package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudoku.SudokuType;
import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


public class SudokuGUI extends JFrame implements SudokuObserver {
    private static final int SIZE = 9;
    private JButton[][] cells;
    private SudokuFacade facade;
    private Sudoku sudoku;
    private JLabel ruleLabel;
    private JComboBox<SudokuType> difficultyComboBox;

    public SudokuGUI() {
        facade = new SudokuFacade();
        setTitle("Sudoku Solver");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton createSudokuButton = new JButton("Create Sudoku");
        JButton stepButton = new JButton("Solver Step");

        difficultyComboBox = new JComboBox<>(SudokuType.values());
        difficultyComboBox.setSelectedItem(SudokuType.RANDOM);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        controlPanel.add(createSudokuButton, gbc);

        gbc.gridy = 1;
        controlPanel.add(difficultyComboBox, gbc);

        gbc.gridy = 2;
        controlPanel.add(stepButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        ruleLabel = new JLabel("Current Rule: None");
        ruleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ruleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(ruleLabel, BorderLayout.NORTH);

        createSudokuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuType selectedType = (SudokuType) difficultyComboBox.getSelectedItem();
                try {
                    sudoku = facade.createSudoku(selectedType);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
                        JOptionPane.showMessageDialog(null, "Sudoku solved!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The Sudoku cannot be solved by the Deduction Rules. Please fill any cell.");
                }
            }
        });
    }

    @Override
    public void updateSudoku() {
        if (sudoku == null) return;

        sudoku.initializePossibleValues();
        int[][] board = sudoku.getSudokuGrid();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                if (value == 0) {
                    Set<Integer> possibleValuesSet = sudoku.getPossibleValues(row, col);
                    if (possibleValuesSet.isEmpty()) {
                        cells[row][col].setText("");
                    } else {
                        List<Integer> possibleValues = new ArrayList<>(possibleValuesSet);

                        StringBuilder possibleValuesText = new StringBuilder("<html><div style='font-size:10px; opacity:0.5;'>");
                        possibleValuesText.append("<table style='width:100%; height:100%;'>");
                        for (int i = 0; i < 3; i++) {
                            possibleValuesText.append("<tr>");
                            for (int j = 0; j < 3; j++) {
                                int index = i * 3 + j;
                                if (index < possibleValues.size()) {
                                    possibleValuesText.append("<td align='center'>").append(possibleValues.get(index)).append("</td>");
                                } else {
                                    possibleValuesText.append("<td></td>");
                                }
                            }
                            possibleValuesText.append("</tr>");
                        }
                        possibleValuesText.append("</table></div></html>");
                        cells[row][col].setText(possibleValuesText.toString());
                        cells[row][col].setForeground(new Color(0, 0, 0, 128));
                    }
                    cells[row][col].setBackground(Color.WHITE);
                } else {
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                    cells[row][col].setForeground(Color.BLACK);
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
                    "Enter the value (1-9) for the cell (" + (row + 1) + ", " + (col + 1) + ") :",
                    "Manual input", JOptionPane.PLAIN_MESSAGE);

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
                        JOptionPane.showMessageDialog(null, "Invalid value. The value must be between 1 and 9.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry. Please enter a valid number. The value must be between 1 and 9.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}