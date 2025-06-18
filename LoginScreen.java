// src/main/java/com/mkcalculadora/LoginScreen.java
package com.mkcalculadora;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Classe para a tela de login/identificação do operador.
 */
public class LoginScreen {

    private Stage loginStage;
    private TextField responsibleField;
    private TextField sectorField;
    private TextField productionLineField;
    private Runnable onLoginSuccess; // Callback para quando o login for bem-sucedido

    public LoginScreen(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
        setupUI();
    }

    private void setupUI() {
        loginStage = new Stage();
        loginStage.setTitle("MK Calculadora MTM - Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getStyleClass().add("grid-pane"); // Para estilização CSS, se desejar

        // Adicionar o logo da MK (placeholder)
        try {
            // Supondo que você tenha uma imagem chamada 'mk_logo.png' ou 'mk_logo.jpg' em src/main/resources/
            // Para testar, você pode colocar uma imagem qualquer na pasta 'resources'
            // Ou usar uma URL de imagem de placeholder se não tiver uma imagem local ainda.
            // Para o ambiente Canvas, é melhor usar uma imagem de placeholder URL.
            Image logoImage = new Image("https://placehold.co/150x150/EEEEEE/333333?text=MK+Logo", true); // true para background loading
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitHeight(100);
            logoView.setFitWidth(100);
            logoView.setPreserveRatio(true);
            grid.add(logoView, 0, 0, 2, 1);
            GridPane.setHalignment(logoView, Pos.CENTER);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem do logo: " + e.getMessage());
            // Fallback para texto simples se a imagem não carregar
            Label appTitle = new Label("MK Calculadora MTM");
            appTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            grid.add(appTitle, 0, 0, 2, 1);
            GridPane.setHalignment(appTitle, Pos.CENTER);
        }


        Label responsibleLabel = new Label("Nome do Operador:");
        grid.add(responsibleLabel, 0, 1);
        responsibleField = new TextField();
        responsibleField.setPromptText("Ex: João Silva");
        responsibleField.getStyleClass().add("text-field");
        grid.add(responsibleField, 1, 1);

        Label sectorLabel = new Label("Setor:");
        grid.add(sectorLabel, 0, 2);
        sectorField = new TextField();
        sectorField.setPromptText("Ex: Montagem");
        sectorField.getStyleClass().add("text-field");
        grid.add(sectorField, 1, 2);

        Label productionLineLabel = new Label("Linha de Produção:");
        grid.add(productionLineLabel, 0, 3);
        productionLineField = new TextField();
        productionLineField.setPromptText("Ex: Linha A1");
        productionLineField.getStyleClass().add("text-field");
        grid.add(productionLineField, 1, 3);

        Button loginButton = new Button("Entrar");
        loginButton.setPrefWidth(200);
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(e -> handleLogin());
        loginButton.getStyleClass().add("button-primary");
        grid.add(loginButton, 0, 4, 2, 1);
        GridPane.setHalignment(loginButton, Pos.CENTER);

        Scene scene = new Scene(grid, 400, 350);
        // Exemplo de CSS básico (pode ser expandido em um arquivo .css)
        scene.getStylesheets().add(getClass().getResource("/login_styles.css").toExternalForm()); // Se você criar um arquivo CSS
        loginStage.setScene(scene);
    }

    private void handleLogin() {
        String responsible = responsibleField.getText().trim();
        String sector = sectorField.getText().trim();
        String productionLine = productionLineField.getText().trim();

        if (responsible.isEmpty() || sector.isEmpty() || productionLine.isEmpty()) {
            // TODO: Exibir uma mensagem de erro para o usuário (e.g., um Dialog ou Label de erro)
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        // Armazenar os dados globalmente ou passá-los para a próxima tela
        CalculadoraMTM.currentResponsible = responsible;
        CalculadoraMTM.currentSector = sector;
        CalculadoraMTM.currentProductionLine = productionLine;

        loginStage.close(); // Fecha a tela de login
        if (onLoginSuccess != null) {
            onLoginSuccess.run(); // Chama o callback para iniciar a tela principal
        }
    }

    public void show() {
        loginStage.show();
    }
}
