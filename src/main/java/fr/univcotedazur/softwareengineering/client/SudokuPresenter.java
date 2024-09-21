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
import javafx.fxml.FXML;
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
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


import static fr.univcotedazur.softwareengineering.sudoku.Sudoku.SIZE;

public class SudokuPresenter extends Application implements DisplayObserver {

    @Getter
    private Button[][] cells;
    private SudokuController controller;
    private MediaPlayer mediaPlayer;
    private Timeline blinkTimeline;
    private Label ruleLabel;
    private ComboBox<SudokuType> difficultyComboBox;
    private ListView<String> ruleList;
    private ObservableList<String> ruleListModel;
    private String currentRuleName;
    private SceneManager sceneManager;
    @FXML
    @Getter
    private Button muteButton;
    @FXML
    @Getter
    private Button startButton;
    @FXML
    @Getter
    private Button createSudokuButton;
    @FXML
    @Getter
    private Button solveButton;
    private static final String FONT = "SansSerif";
    private static final String BUTTON_STYLE = "-fx-background-color: #007BFF; -fx-text-fill: white;";
    private static final String RULE_HEADER = "Current Rule: ";

    @Override
    public void start(Stage primaryStage) {
        String musicFile = Objects.requireNonNull(getClass().getResource("/audio/please-calm-my-mind.mp3")).toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        primaryStage.getIcons().add(new Image("/images/sudoku.png"));
        primaryStage.setTitle("Sudoku Solver");
        sceneManager = new SceneManager(primaryStage);
        sceneManager.setStartScene(createStartScene());
        sceneManager.setGameScene(createGameScene());
        sceneManager.showStartScene();

        startCellBlinking();
    }

    @Override
    public void updateSudoku(Sudoku sudoku) {
        if (sudoku == null) return;

        int[][] board = getSudokuGrid(sudoku);
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
                    cells[row][col].setOnAction(null);
                    cells[row][col].setText(String.valueOf(value));
                    cells[row][col].setGraphic(null);
                    cells[row][col].setStyle("-fx-background-color: #C8C8C8;");
                    cells[row][col].setTextFill(Color.BLACK);
                }
            }
        }
    }

    private Scene createStartScene() {
        VBox startLayout = new VBox(20);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setPadding(new Insets(20));
        startLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label title = new Label("Sudoku Solver");
        title.setFont(Font.font(FONT, 36));
        title.setTextFill(Color.web("#333333"));

        startButton = new Button("Start");
        startButton.setStyle(BUTTON_STYLE);
        startButton.setOnAction(event -> sceneManager.showGameScene());

        startLayout.getChildren().addAll(title, startButton);
        return new Scene(startLayout, 1000, 800);
    }

    private Scene createGameScene() {
        controller = new SudokuController();

        cells = new Button[SIZE][SIZE];

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5F5F5"), CornerRadii.EMPTY, Insets.EMPTY)));

        GridPane boardPanel = new GridPane();
        boardPanel.setHgap(5);
        boardPanel.setVgap(5);
        boardPanel.setPadding(new Insets(10));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button cell = new Button("");
                cell.setFont(Font.font(FONT, 24));
                cell.setPrefSize(80, 80);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B4B4B4;");
                cells[row][col] = cell;
                boardPanel.add(cell, col, row);
            }
        }
        mainLayout.setCenter(boardPanel);

        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.TOP_LEFT);
        controlPanel.setStyle("-fx-background-color: #F0F0F0;");

        createSudokuButton = new Button("Create Sudoku");
        createSudokuButton.setStyle(BUTTON_STYLE);
        createSudokuButton.setOnAction(event -> createSudoku());

        solveButton = new Button("Solve");
        solveButton.setStyle(BUTTON_STYLE);
        solveButton.setOnAction(event -> solveStep());
        solveButton.setDisable(true);

        difficultyComboBox = new ComboBox<>(FXCollections.observableArrayList(SudokuType.values()));
        difficultyComboBox.setValue(SudokuType.RANDOM);
        difficultyComboBox.setTooltip(new Tooltip("Select the difficulty level of the Sudoku"));

        ruleListModel = FXCollections.observableArrayList();
        ruleList = new ListView<>(ruleListModel);
        ruleList.setPrefHeight(200);

        controlPanel.getChildren().addAll(createSudokuButton, difficultyComboBox, solveButton, ruleList);
        mainLayout.setRight(controlPanel);

        currentRuleName = "None";
        ruleLabel = new Label(RULE_HEADER + currentRuleName);
        ruleLabel.setFont(Font.font(FONT, 18));
        ruleLabel.setTextFill(Color.web("#333333"));
        mainLayout.setTop(ruleLabel);

        muteButton = new Button("Mute");
        muteButton.setStyle(BUTTON_STYLE);
        muteButton.setOnAction(event -> toggleMute());

        HBox topRightControls = new HBox(muteButton);
        topRightControls.setAlignment(Pos.TOP_RIGHT);
        mainLayout.setTop(new VBox(ruleLabel, topRightControls));

        return new Scene(mainLayout, 1000, 800);
    }

    private void createSudoku() {
        SudokuType selectedType = difficultyComboBox.getValue();
        try {
            if (blinkTimeline != null) {
                blinkTimeline.stop();
            }
            Sudoku sudoku = controller.createSudoku(selectedType);
            SudokuChecker sudokuChecker = new SudokuChecker(this);
            sudoku.addObserver(sudokuChecker);
            sudoku.addObserver(this);
            updateSudoku(sudoku);
            currentRuleName = "None";
            ruleLabel.setText(RULE_HEADER + currentRuleName);
            updateRuleList();

            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if(sudoku.getValue(row, col) == 0) {
                        int finalRow = row;
                        int finalCol = col;
                        cells[row][col].setOnAction(event -> cellClicked(finalRow, finalCol));
                    }
                    cells[row][col].setDisable(false);
                }
            }

            solveButton.setDisable(false);
        } catch (IOException ex) {
            sceneManager.showError("Error creating Sudoku: " + ex.getMessage());
        }
    }

    private void solveStep() {
        String ruleName = controller.solve();
        if (ruleName != null) {
            ruleLabel.setText(RULE_HEADER + ruleName);
            currentRuleName = ruleName;
            updateRuleList();
            if (controller.getSudoku().isSolved()) {
                sceneManager.showInfo("Sudoku solved!");
            }
        } else {
            sceneManager.showWarning("The Sudoku cannot be solved by the Deduction Rules. Please fill any cell.");
        }
    }

    private void toggleMute() {
        mediaPlayer.setMute(!mediaPlayer.isMute());
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
                    currentRuleName = "None";
                    ruleLabel.setText(RULE_HEADER + currentRuleName);
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
        blinkTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int row = (int) (Math.random() * SIZE);
            int col = (int) (Math.random() * SIZE);
            Button cell = cells[row][col];

            cell.setStyle("-fx-background-color: #007BFF;");

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> cell.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #B4B4B4;"));
            pause.play();
        }));

        blinkTimeline.setCycleCount(Animation.INDEFINITE);
        blinkTimeline.play();
    }

    private int[][] getSudokuGrid(Sudoku sudoku) {
        int[][] sudokuAsGrid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sudokuAsGrid[i][j] = sudoku.getValue(i, j);
            }
        }
        return sudokuAsGrid;
    }
}
