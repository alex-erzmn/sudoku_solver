package fr.univcotedazur.softwareengineering.client;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import fr.univcotedazur.softwareengineering.sudoku.SudokuType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import static fr.univcotedazur.softwareengineering.sudoku.Sudoku.SIZE;
import static fr.univcotedazur.softwareengineering.sudoku.Sudoku.isSolved;

public class SudokuPresenter extends Application implements SudokuObserver {

    private Button[][] cells;
    private SudokuController controller;
    private MediaPlayer mediaPlayer;
    private Timeline blinkTimeline;
    private Label ruleLabel;
    private ComboBox<SudokuType> difficultyComboBox;
    private ListView<String> ruleList;
    private ObservableList<String> ruleListModel;
    private String currentRuleName;
    private SceneManager sceneManager; // Add SceneManager
    private static final String FONT = "SansSerif";
    private static final String BUTTONSTYLE = "-fx-background-color: #007BFF; -fx-text-fill: white;";

    @Override
    public void start(Stage primaryStage) {
        String musicFile = getClass().getResource("/audio/please-calm-my-mind.mp3").toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Schleife die Musik
        mediaPlayer.play();

        primaryStage.getIcons().add(new Image("/images/sudoku.png"));
        primaryStage.setTitle("Sudoku Solver");
        sceneManager = new SceneManager(primaryStage);
        sceneManager.setStartScene(createStartScene()); // Set the start scene
        sceneManager.setGameScene(createGameScene());   // Set the game scene
        sceneManager.showStartScene();// Show the start scene

        startCellBlinking();
    }

    private Scene createStartScene() {
        VBox startLayout = new VBox(20);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setPadding(new Insets(20));
        startLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label title = new Label("Sudoku Solver");
        title.setFont(Font.font(FONT, 36));
        title.setTextFill(Color.web("#333333"));

        Button startButton = new Button("Start");
        startButton.setStyle(BUTTONSTYLE);
        startButton.setOnAction(event -> sceneManager.showGameScene()); // Show game scene on button click

        startLayout.getChildren().addAll(title, startButton);
        return new Scene(startLayout, 1000, 800);
    }

    private Scene createGameScene() {
        controller = new SudokuController(); // Assuming this is how you initialize it

        // Initialize the cells array
        cells = new Button[SIZE][SIZE];

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Sudoku Board Panel
        GridPane boardPanel = new GridPane();
        boardPanel.setHgap(5);
        boardPanel.setVgap(5);
        boardPanel.setPadding(new Insets(10));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button cell = new Button("");
                cell.setFont(Font.font(FONT, 24)); // Optional: larger font size
                cell.setPrefSize(80, 80); // Larger cells
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B4B4B4;");
                cell.setDisable(true);
                int finalRow = row;
                int finalCol = col;
                cell.setOnAction(event -> cellClicked(finalRow, finalCol));
                cells[row][col] = cell;
                boardPanel.add(cell, col, row);
            }
        }
        mainLayout.setCenter(boardPanel);

        // Control Panel
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.TOP_LEFT);
        controlPanel.setStyle("-fx-background-color: #F0F0F0;");

        Button createSudokuButton = new Button("Create Sudoku");
        createSudokuButton.setStyle(BUTTONSTYLE);
        createSudokuButton.setOnAction(event -> createSudoku());

        Button solveButton = new Button("Solve");
        solveButton.setStyle(BUTTONSTYLE);
        solveButton.setOnAction(event -> solveStep());

        difficultyComboBox = new ComboBox<>(FXCollections.observableArrayList(SudokuType.values()));
        difficultyComboBox.setValue(SudokuType.RANDOM);
        difficultyComboBox.setTooltip(new Tooltip("Select the difficulty level of the Sudoku"));

        ruleListModel = FXCollections.observableArrayList();
        ruleList = new ListView<>(ruleListModel);
        ruleList.setPrefHeight(200);

        controlPanel.getChildren().addAll(createSudokuButton, difficultyComboBox, solveButton, ruleList);
        mainLayout.setRight(controlPanel);

        // Rule Label
        ruleLabel = new Label("Current Rule: None");
        ruleLabel.setFont(Font.font(FONT, 18));
        ruleLabel.setTextFill(Color.web("#333333"));
        mainLayout.setTop(ruleLabel);

        // Mute-Button erstellen
        Button muteButton = new Button("Mute");
        muteButton.setStyle(BUTTONSTYLE);
        muteButton.setOnAction(event -> toggleMute());

        // Hinzufügen des Mute-Buttons in die oberste rechte Ecke
        HBox topRightControls = new HBox(muteButton);
        topRightControls.setAlignment(Pos.TOP_RIGHT);
        mainLayout.setTop(new VBox(ruleLabel, topRightControls));

        return new Scene(mainLayout, 1000, 800);
    }

    private void toggleMute() {
        if (mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
        } else {
            mediaPlayer.setMute(true);
        }
    }


    private void createSudoku() {
        SudokuType selectedType = difficultyComboBox.getValue();
        try {
            if (blinkTimeline != null) {
                blinkTimeline.stop();
            }
            Sudoku sudoku = controller.createSudoku(selectedType);
            sudoku.addObserver(this);
            updateSudoku(sudoku);
            ruleLabel.setText("Current Rule: None");
            updateRuleList();

            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setDisable(false);
                }
            }
        } catch (IOException ex) {
            sceneManager.showError("Error creating Sudoku: " + ex.getMessage());
        }
    }

    private void solveStep() {
        String ruleName = controller.solve();
        if (ruleName != null) {
            ruleLabel.setText("Current Rule: " + ruleName);
            currentRuleName = ruleName;
            updateRuleList();
            if (isSolved(controller.getSudoku())) {
                sceneManager.showInfo("Sudoku solved!");
            }
        } else {
            sceneManager.showWarning("The Sudoku cannot be solved by the Deduction Rules. Please fill any cell.");
        }
    }

    private void cellClicked(int row, int col) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Manual Input");
        dialog.setHeaderText("Enter the value (1-9) for the cell (" + (row + 1) + ", " + (col + 1) + "):");
        dialog.setContentText("Value:");

        String input = dialog.showAndWait().orElse("");
        if (!input.trim().isEmpty()) {
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 9) {
                    controller.setCell(row, col, value);
                    updateSudoku(controller.getSudoku());
                    ruleLabel.setText("Current Rule: None");
                    currentRuleName = "None";
                    updateRuleList();
                } else {
                    sceneManager.showError("Invalid value. The value must be between 1 and 9.");
                }
            } catch (NumberFormatException ex) {
                sceneManager.showError("Invalid entry. Please enter a valid number.");
            }
        }
    }



    private void updateRuleList() {
        ruleListModel.setAll(controller.getDeductionRules());
        if (currentRuleName != null) {
            ruleList.getSelectionModel().select(currentRuleName);
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
                    List<Integer> possibleValues = new ArrayList<>(possibleValuesSet);
                    cells[row][col].setGraphic(createPossibleValuesGrid(possibleValues));
                    cells[row][col].setText("");
                    cells[row][col].setStyle("-fx-background-color: #FFFFFF;");
                } else {
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setGraphic(null); // Remove any previous graphics
                    cells[row][col].setStyle("-fx-background-color: #C8C8C8;");
                    cells[row][col].setTextFill(Color.BLACK);
                }
            }
        }
    }

    private GridPane createPossibleValuesGrid(List<Integer> possibleValues) {
        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);

        for (int i = 0; i < 9; i++) {
            int value = (i < possibleValues.size()) ? possibleValues.get(i) : 0;
            Label label = new Label(value == 0 ? "" : String.valueOf(value));
            label.setFont(Font.font(FONT, 10));
            label.setAlignment(Pos.CENTER);
            grid.add(label, i % 3, i / 3);
        }
        return grid;
    }

    private void startCellBlinking() {
        // Timeline für das Blinken erstellen
        blinkTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            // Eine zufällige Zelle auswählen
            int row = (int) (Math.random() * SIZE);
            int col = (int) (Math.random() * SIZE);
            Button cell = cells[row][col];

            // Zelle hervorheben
            cell.setStyle("-fx-background-color: #007BFF;"); // Goldene Farbe zum Hervorheben

            // Rücksetzung nach kurzer Zeit
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> cell.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B4B4B4;"));
            pause.play();
        }));

        // Die Timeline kontinuierlich ablaufen lassen
        blinkTimeline.setCycleCount(Animation.INDEFINITE);
        blinkTimeline.play();
    }
}
