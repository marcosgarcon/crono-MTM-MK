// src/main/java/com/mkcalculadora/CalculadoraMTM.java
package com.mkcalculadora;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image; // Para ícones de botões, se desejar
import javafx.scene.image.ImageView; // Para ícones de botões, se desejar
import javafx.scene.control.Alert.AlertType; // Para mensagens de alerta

import java.util.Optional;

/**
 * A classe principal da aplicação de Cronoanálise Industrial baseada em MTM.
 * Utiliza JavaFX para a interface gráfica.
 */
public class CalculadoraMTM extends Application {

    // Variáveis globais para armazenar os dados de login
    public static String currentResponsible = "";
    public static String currentSector = "";
    public static String currentProductionLine = "";

    private ObservableList<Activity> activities = FXCollections.observableArrayList();
    private TableView<Activity> activitiesTable;
    private TextField activityDescriptionField;
    private TextField manualTimeField;
    private Label totalTimeLabel;

    private ReportGenerator reportGenerator = new ReportGenerator(); // Instância do gerador de relatórios

    @Override
    public void start(Stage primaryStage) {
        // Exibir a tela de login primeiro
        LoginScreen loginScreen = new LoginScreen(() -> {
            // Este Runnable será executado quando o login for bem-sucedido
            primaryStage.setTitle("MK Calculadora MTM - Cronoanálise Industrial");
            setupMainUI(primaryStage);
            primaryStage.show();
        });
        loginScreen.show();
    }

    private void setupMainUI(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStyleClass().add("root-pane"); // Para estilização CSS

        // --- Top: Informações do Operador ---
        HBox userInfoBox = new HBox(20);
        userInfoBox.setPadding(new Insets(10));
        userInfoBox.setAlignment(Pos.CENTER_LEFT);
        userInfoBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        Label responsibleInfo = new Label("Operador: " + currentResponsible);
        Label sectorInfo = new Label("Setor: " + currentSector);
        Label lineInfo = new Label("Linha: " + currentProductionLine);
        userInfoBox.getChildren().addAll(responsibleInfo, sectorInfo, lineInfo);
        root.setTop(userInfoBox);


        // --- Centro: Entrada de Atividades e Tabela ---
        GridPane centerLayout = new GridPane();
        centerLayout.setHgap(10);
        centerLayout.setVgap(10);
        centerLayout.setPadding(new Insets(10));
        centerLayout.getStyleClass().add("grid-pane");

        // Campo para descrição da atividade
        Label descriptionLabel = new Label("Descrição da Atividade:");
        activityDescriptionField = new TextField();
        activityDescriptionField.setPromptText("Ex: Montar componente X");
        activityDescriptionField.getStyleClass().add("text-field");
        centerLayout.add(descriptionLabel, 0, 0);
        centerLayout.add(activityDescriptionField, 1, 0);

        // Campo para tempo manual (placeholder para MTM)
        Label manualTimeLabel = new Label("Tempo Padrão (unidades MTM):");
        manualTimeField = new TextField();
        manualTimeField.setPromptText("Ex: 1.5 (tempo em TMUs ou segundos)");
        manualTimeField.getStyleClass().add("text-field");
        centerLayout.add(manualTimeLabel, 0, 1);
        centerLayout.add(manualTimeField, 1, 1);

        // Botão para adicionar atividade
        Button addActivityButton = new Button("Adicionar Atividade");
        addActivityButton.setPrefWidth(200);
        addActivityButton.setOnAction(e -> addActivity());
        addActivityButton.getStyleClass().add("button-success");
        centerLayout.add(addActivityButton, 0, 2, 2, 1);
        GridPane.setHalignment(addActivityButton, Pos.CENTER);


        // Botão para abrir a tela de análise de vídeo
        Button videoAnalysisButton = new Button("Analisar via Vídeo");
        videoAnalysisButton.setPrefWidth(200);
        videoAnalysisButton.setOnAction(e -> {
            VideoAnalysisScreen videoScreen = new VideoAnalysisScreen();
            videoScreen.show();
        });
        videoAnalysisButton.getStyleClass().add("button-info"); // Estilo diferente para o botão de vídeo
        centerLayout.add(videoAnalysisButton, 0, 3, 2, 1);
        GridPane.setHalignment(videoAnalysisButton, Pos.CENTER);


        // Tabela de atividades
        activitiesTable = new TableView<>();
        activitiesTable.setItems(activities);
        activitiesTable.setPlaceholder(new Label("Nenhuma atividade adicionada ainda."));
        activitiesTable.getStyleClass().add("table-view");

        TableColumn<Activity, String> descriptionCol = new TableColumn<>("Descrição");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setPrefWidth(300);

        TableColumn<Activity, Double> timeCol = new TableColumn<>("Tempo Padrão");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("standardTime"));
        timeCol.setPrefWidth(150);

        activitiesTable.getColumns().addAll(descriptionCol, timeCol);

        VBox tableContainer = new VBox(activitiesTable);
        tableContainer.setPadding(new Insets(10, 0, 0, 0));
        centerLayout.add(tableContainer, 0, 4, 2, 1);
        GridPane.setVgrow(tableContainer, Priority.ALWAYS); // Faz a tabela crescer verticalmente


        // --- Bottom: Controles e Resultados ---
        VBox bottomLayout = new VBox(10);
        bottomLayout.setPadding(new Insets(10));
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.getStyleClass().add("bottom-box");

        HBox controlButtons = new HBox(15);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setPadding(new Insets(10, 0, 0, 0));

        Button calculateTotalButton = new Button("Calcular Tempo Total");
        calculateTotalButton.setPrefWidth(180);
        calculateTotalButton.setOnAction(e -> calculateTotalTime());
        calculateTotalButton.getStyleClass().add("button-info");

        Button clearButton = new Button("Limpar Tudo");
        clearButton.setPrefWidth(180);
        clearButton.setOnAction(e -> clearAll());
        clearButton.getStyleClass().add("button-danger");

        Button saveReportButton = new Button("Salvar Relatório (TODO)"); // Placeholder for future session history
        saveReportButton.setPrefWidth(180);
        saveReportButton.setOnAction(e -> showAlert("Funcionalidade em desenvolvimento", "O histórico da sessão e salvamento de múltiplas análises será implementado no futuro.", AlertType.INFORMATION));
        saveReportButton.getStyleClass().add("button-default");

        controlButtons.getChildren().addAll(calculateTotalButton, clearButton, saveReportButton);

        totalTimeLabel = new Label("Tempo Padrão Total: 0.00 unidades");
        totalTimeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        bottomLayout.getChildren().addAll(controlButtons, totalTimeLabel);


        // --- Menu Superior (para exportação) ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Arquivo");

        MenuItem exportPdfItem = new MenuItem("Exportar para PDF");
        exportPdfItem.setOnAction(e -> reportGenerator.exportToPdf(
                activities, currentResponsible, currentSector, currentProductionLine, calculateTotalTimeValue()));

        MenuItem exportExcelItem = new MenuItem("Exportar para Excel");
        exportExcelItem.setOnAction(e -> reportGenerator.exportToExcel(
                activities, currentResponsible, currentSector, currentProductionLine, calculateTotalTimeValue()));

        MenuItem exitItem = new MenuItem("Sair");
        exitItem.setOnAction(e -> primaryStage.close());

        fileMenu.getItems().addAll(exportPdfItem, exportExcelItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);

        VBox topContainer = new VBox(menuBar, userInfoBox);
        root.setTop(topContainer); // Set top content to VBox containing MenuBar and userInfoBox

        root.setCenter(centerLayout);
        root.setBottom(bottomLayout);

        Scene scene = new Scene(root, 800, 600); // Tamanho inicial da janela
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Adicione seu CSS aqui
        primaryStage.setScene(scene);
    }

    /**
     * Adiciona uma atividade à tabela e lista.
     */
    private void addActivity() {
        String description = activityDescriptionField.getText().trim();
        String timeText = manualTimeField.getText().trim();

        if (description.isEmpty() || timeText.isEmpty()) {
            showAlert("Erro de Entrada", "Por favor, preencha a descrição da atividade e o tempo padrão.", AlertType.WARNING);
            return;
        }

        try {
            double standardTime = Double.parseDouble(timeText);
            if (standardTime < 0) {
                showAlert("Erro de Entrada", "O tempo padrão não pode ser negativo.", AlertType.WARNING);
                return;
            }
            activities.add(new Activity(description, standardTime));
            activityDescriptionField.clear();
            manualTimeField.clear();
            calculateTotalTime(); // Recalcula o total ao adicionar uma atividade
        } catch (NumberFormatException e) {
            showAlert("Erro de Entrada", "O tempo padrão deve ser um número válido.", AlertType.ERROR);
        }
    }

    /**
     * Calcula e exibe o tempo total padrão das atividades.
     */
    private void calculateTotalTime() {
        double total = calculateTotalTimeValue();
        totalTimeLabel.setText(String.format("Tempo Padrão Total: %.2f unidades", total));
    }

    /**
     * Retorna o valor do tempo total padrão.
     */
    private double calculateTotalTimeValue() {
        return activities.stream()
                         .mapToDouble(Activity::getStandardTime)
                         .sum();
    }

    /**
     * Limpa todas as atividades e campos.
     */
    private void clearAll() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Limpeza");
        alert.setHeaderText("Você tem certeza que deseja limpar todas as atividades?");
        alert.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            activities.clear();
            activityDescriptionField.clear();
            manualTimeField.clear();
            calculateTotalTime(); // Reseta o total para 0.00
            showAlert("Limpeza Concluída", "Todas as atividades foram removidas.", AlertType.INFORMATION);
        }
    }

    /**
     * Exibe uma mensagem de alerta.
     * @param title O título do alerta.
     * @param message A mensagem do alerta.
     * @param type O tipo de alerta (INFORMATION, WARNING, ERROR, etc.).
     */
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // Sem cabeçalho secundário
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
