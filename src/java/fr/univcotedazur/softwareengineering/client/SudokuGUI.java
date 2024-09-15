package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The GUI for the Sudoku Solver. This class is responsible for displaying the Sudoku board and the control panel.
 * The GUI allows the user to create a Sudoku, solve it step by step, and manually fill the cells.
 * The GUI also displays the current rule that is being applied to solve the Sudoku.
 * @since 12/09/2024
 */
public class SudokuGUI extends JFrame implements SudokuObserver {
    private static final int SIZE = 9;
    private JButton[][] cells;
    private final SudokuController controller;
    private JLabel ruleLabel;
    private JComboBox<SudokuType> difficultyComboBox;
    private JList<String> ruleList;
    private DefaultListModel<String> ruleListModel;
    private String currentRuleName;

    public SudokuGUI(SudokuController controller) {
        this.controller = controller;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Sudoku Solver");
        setSize(1200, 800); // Vergrößerte Fenstergröße
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Hintergrundfarbe des Fensters

        // Sudoku Board Panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(new Color(255, 255, 255)); // Hintergrundfarbe des Boards

        cells = new JButton[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JButton("");
                cells[row][col].setFont(new Font("SansSerif", Font.BOLD, 24));
                cells[row][col].setFocusPainted(false);
                cells[row][col].setBackground(new Color(240, 240, 250)); // Hintergrundfarbe der Zellen
                cells[row][col].setOpaque(true);
                cells[row][col].setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
                cells[row][col].setPreferredSize(new Dimension(70, 70)); // Einheitliche Größe für alle Zellen
                cells[row][col].setHorizontalAlignment(SwingConstants.CENTER); // Zentriert den Text
                cells[row][col].setVerticalAlignment(SwingConstants.CENTER); // Zentriert den Text
                cells[row][col].addActionListener(new CellButtonListener(row, col));
                boardPanel.add(cells[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.setBackground(new Color(240, 240, 240)); // Hintergrundfarbe des Steuerungsbereichs

        JButton createSudokuButton = new JButton("Create Sudoku");
        createSudokuButton.setBackground(new Color(0, 123, 255)); // Hintergrundfarbe des Buttons
        createSudokuButton.setForeground(Color.WHITE); // Textfarbe des Buttons
        createSudokuButton.setFocusPainted(false);
        JButton stepButton = new JButton("Solver Step");
        stepButton.setBackground(new Color(0, 123, 255));
        stepButton.setForeground(Color.WHITE);
        stepButton.setFocusPainted(false);

        difficultyComboBox = new JComboBox<>(SudokuType.values());
        difficultyComboBox.setSelectedItem(SudokuType.RANDOM);
        difficultyComboBox.setToolTipText("Select the difficulty level of the Sudoku");

        ruleListModel = new DefaultListModel<>();
        ruleList = new JList<>(ruleListModel);
        ruleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ruleList.setVisibleRowCount(15);
        JScrollPane ruleScrollPane = new JScrollPane(ruleList);
        ruleScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 1), "Available Rules"));
        ruleScrollPane.setBackground(new Color(255, 255, 255)); // Hintergrundfarbe der ScrollPane
        ruleList.setBackground(new Color(255, 255, 255)); // Hintergrundfarbe der JList

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(createSudokuButton, gbc);

        gbc.gridy = 1;
        controlPanel.add(difficultyComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        controlPanel.add(stepButton, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        controlPanel.add(ruleScrollPane, gbc);

        add(controlPanel, BorderLayout.EAST);

        // Rule Label
        ruleLabel = new JLabel("Current Rule: None");
        ruleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        ruleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ruleLabel.setForeground(new Color(50, 50, 50)); // Textfarbe der Regel-Label
        ruleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Abstand um das Label
        add(ruleLabel, BorderLayout.NORTH);

        // Button Listeners
        createSudokuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuType selectedType = (SudokuType) difficultyComboBox.getSelectedItem();
                try {
                    Sudoku sudoku = controller.createSudoku(selectedType);
                    sudoku.addObserver(SudokuGUI.this);
                    updateSudoku(sudoku);
                    ruleLabel.setText("Current Rule: None");
                    updateRuleList(); // Regel-Liste aktualisieren
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SudokuGUI.this, "Error creating Sudoku: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruleName = controller.step();
                if (ruleName != null) {
                    ruleLabel.setText("Current Rule: " + ruleName);
                    currentRuleName = ruleName;
                    updateRuleList(); // Regel-Liste aktualisieren
                    if (isSolved(controller.getSudoku())) {
                        JOptionPane.showMessageDialog(SudokuGUI.this, "Sudoku solved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SudokuGUI.this, "The Sudoku cannot be solved by the Deduction Rules. Please fill any cell.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void updateRuleList() {
        ruleListModel.clear();
        for (String rule : controller.getDeductionRules()) {
            ruleListModel.addElement(rule);
        }
        if (currentRuleName != null) {
            ruleList.setSelectedValue(currentRuleName, true);
        }
    }

    @Override
    public void updateSudoku(Sudoku sudoku) {
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
                        cells[row][col].setText(createPossibleValuesText(possibleValues));
                        cells[row][col].setForeground(new Color(0, 0, 0, 128));
                    }
                    cells[row][col].setBackground(new Color(255, 255, 255)); // Hintergrundfarbe für leere Zellen
                } else {
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setBackground(new Color(200, 200, 200)); // Hintergrundfarbe für Zellen mit Werten
                    cells[row][col].setForeground(Color.BLACK);
                }
            }
        }
    }

    private String createPossibleValuesText(List<Integer> possibleValues) {
        StringBuilder possibleValuesText = new StringBuilder("<html><div style='font-size:12px; text-align:center;'>");
        possibleValuesText.append("<table style='width:100%; height:100%; border-spacing:0; border-collapse:collapse;'>");
        for (int i = 0; i < 3; i++) {
            possibleValuesText.append("<tr>");
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < possibleValues.size()) {
                    possibleValuesText.append("<td style='border: 1px solid #ccc; width:33%; height:33%; padding:2px;'>")
                            .append(possibleValues.get(index))
                            .append("</td>");
                } else {
                    possibleValuesText.append("<td style='border: 1px solid #ccc; width:33%; height:33%;'></td>");
                }
            }
            possibleValuesText.append("</tr>");
        }
        possibleValuesText.append("</table></div></html>");
        return possibleValuesText.toString();
    }

    private boolean isSolved(Sudoku sudoku) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudoku.getValue(row, col) == 0) {
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
            String input = JOptionPane.showInputDialog(SudokuGUI.this,
                    "Enter the value (1-9) for the cell (" + (row + 1) + ", " + (col + 1) + ") :",
                    "Manual input", JOptionPane.PLAIN_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    int value = Integer.parseInt(input);
                    if (value >= 1 && value <= 9) {
                        controller.setCell(row, col, value);
                        updateSudoku(controller.getSudoku());
                        ruleLabel.setText("Current Rule: None");
                        currentRuleName = "None";
                        updateRuleList();
                    } else {
                        JOptionPane.showMessageDialog(SudokuGUI.this, "Invalid value. The value must be between 1 and 9.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SudokuGUI.this, "Invalid entry. Please enter a valid number. The value must be between 1 and 9.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
