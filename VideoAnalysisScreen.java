// src/main/java/com/mkcalculadora/VideoAnalysisScreen.java
package com.mkcalculadora;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para a tela de Análise de Vídeo MTM.
 * Este é um esboço que demonstra a integração de um player de vídeo.
 * A lógica de marcação de movimentos MTM e cálculo precisará ser expandida.
 */
public class VideoAnalysisScreen {

    private Stage stage;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Label currentStatusLabel;
    private ListView<String> markedEventsListView;
    private List<MarkedEvent> markedEvents = new ArrayList<>();

    public VideoAnalysisScreen() {
        setupUI();
    }

    private void setupUI() {
        stage = new Stage();
        stage.setTitle("MK Calculadora MTM - Análise de Vídeo");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.getStyleClass().add("root-pane"); // Para estilização CSS

        // --- Área do Player de Vídeo ---
        mediaView = new MediaView();
        mediaView.setFitWidth(800); // Ajustar largura do player
        mediaView.setFitHeight(450); // Ajustar altura do player
        mediaView.setStyle("-fx-background-color: black;"); // Fundo preto para o player
        mediaView.setPreserveRatio(true);

        VBox videoContainer = new VBox(10);
        videoContainer.setAlignment(Pos.CENTER);
        videoContainer.getChildren().add(mediaView);
        root.setCenter(videoContainer);

        // --- Controles de Vídeo ---
        HBox videoControls = new HBox(15);
        videoControls.setAlignment(Pos.CENTER);
        videoControls.setPadding(new Insets(10, 0, 10, 0));

        Button loadVideoButton = new Button("Carregar Vídeo");
        loadVideoButton.setOnAction(e -> loadVideo());
        loadVideoButton.getStyleClass().add("button-primary");

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.play();
            updateStatus("Reproduzindo...");
        });
        playButton.getStyleClass().add("button-success");

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            if (mediaPlayer != null) mediaPlayer.pause();
            updateStatus("Pausado.");
        });
        pauseButton.getStyleClass().add("button-danger");

        Button markStartButton = new Button("Marcar Início MTM");
        markStartButton.setOnAction(e -> markTimestamp("INÍCIO MTM"));
        markStartButton.getStyleClass().add("button-info");

        Button markEndButton = new Button("Marcar Fim MTM");
        markEndButton.setOnAction(e -> markTimestamp("FIM MTM"));
        markEndButton.getStyleClass().add("button-info");

        videoControls.getChildren().addAll(loadVideoButton, playButton, pauseButton, markStartButton, markEndButton);
        root.setTop(videoControls); // Coloca os controles na parte superior, acima do vídeo.

        // --- Área de Eventos Marcados ---
        VBox eventArea = new VBox(10);
        eventArea.setPadding(new Insets(10));
        eventArea.setAlignment(Pos.TOP_LEFT);
        eventArea.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Label eventsTitle = new Label("Eventos Marcados e Tempos:");
        eventsTitle.setStyle("-fx-font-weight: bold;");
        markedEventsListView = new ListView<>();
        markedEventsListView.setPrefHeight(200); // Altura da lista
        markedEventsListView.getStyleClass().add("list-view");

        currentStatusLabel = new Label("Status: Nenhum vídeo carregado.");
        currentStatusLabel.setStyle("-fx-font-weight: bold;");

        Button analyzeMtmButton = new Button("Analisar Movimentos MTM");
        analyzeMtmButton.setOnAction(e -> analyzeMarkedMovements());
        analyzeMtmButton.getStyleClass().add("button-success");

        eventArea.getChildren().addAll(eventsTitle, markedEventsListView, currentStatusLabel, analyzeMtmButton);
        root.setBottom(eventArea); // Coloca a área de eventos na parte inferior

        Scene scene = new Scene(root, 1000, 750); // Tamanho da janela
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    /**
     * Carrega um arquivo de vídeo selecionado pelo usuário.
     */
    private void loadVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Vídeo para Análise");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de Vídeo", "*.mp4", "*.mov", "*.avi", "*.mkv")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.stop(); // Para qualquer vídeo anterior
                    mediaPlayer.dispose(); // Libera recursos
                }
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);

                mediaPlayer.setOnReady(() -> {
                    updateStatus("Vídeo carregado: " + file.getName());
                    mediaPlayer.play(); // Inicia a reprodução automaticamente ao carregar
                });

                mediaPlayer.setOnEndOfMedia(() -> {
                    updateStatus("Fim do vídeo.");
                    mediaPlayer.seek(Duration.ZERO); // Volta para o início
                });

            } catch (Exception e) {
                updateStatus("Erro ao carregar vídeo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Marca um timestamp no vídeo e o adiciona à lista de eventos.
     * @param eventType O tipo de evento a ser marcado (ex: "INÍCIO MTM", "FIM MTM").
     */
    private void markTimestamp(String eventType) {
        if (mediaPlayer != null && mediaPlayer.getCurrentTime() != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            String timeString = String.format("%.2f s", currentTime.toSeconds());
            markedEvents.add(new MarkedEvent(eventType, currentTime.toSeconds()));
            markedEventsListView.getItems().add(eventType + ": " + timeString);
            updateStatus("Evento marcado: " + eventType + " em " + timeString);
        } else {
            updateStatus("Erro: Nenhum vídeo carregado ou tempo indisponível.");
        }
    }

    /**
     * Lógica para analisar os movimentos MTM com base nos eventos marcados.
     * Esta é a parte crucial para integrar a tabela MTM.
     */
    private void analyzeMarkedMovements() {
        if (markedEvents.isEmpty()) {
            updateStatus("Nenhum evento marcado para análise.");
            return;
        }

        // TODO: Implementar a lógica de análise MTM aqui.
        // Você percorreria a lista 'markedEvents', identificaria pares de início/fim,
        // calcularia a duração de cada segmento e, mais importante,
        // permitiria ao usuário associar esses segmentos a movimentos MTM específicos.
        // Por exemplo:
        // for (int i = 0; i < markedEvents.size(); i += 2) {
        //     if (i + 1 < markedEvents.size()) {
        //         MarkedEvent start = markedEvents.get(i);
        //         MarkedEvent end = markedEvents.get(i + 1);
        //         if (start.eventType.contains("INÍCIO") && end.eventType.contains("FIM")) {
        //             double duration = end.timeInSeconds - start.timeInSeconds;
        //             // Aqui você pediria ao usuário para classificar este segmento
        //             // como um movimento MTM (ex: "Alcançar 30cm", "Agarrar tipo 1")
        //             // e usaria sua MTMCalculator para obter o tempo padrão.
        //             // Você também pode ter uma interface para o usuário inserir
        //             // os parâmetros daquele movimento (distância, peso, etc.)
        //             // e então calcular o TMU.
        //             System.out.println("Segmento de " + duration + "s entre " + start.timeInSeconds + " e " + end.timeInSeconds);
        //         }
        //     }
        // }

        // Exemplo simples de como você pode somar os tempos cronometrados (não MTM puro)
        double totalObservedTime = 0.0;
        if (markedEvents.size() >= 2 && markedEvents.get(0).eventType.contains("INÍCIO MTM") && markedEvents.get(markedEvents.size()-1).eventType.contains("FIM MTM")) {
             totalObservedTime = markedEvents.get(markedEvents.size()-1).timeInSeconds - markedEvents.get(0).timeInSeconds;
             updateStatus(String.format("Tempo total observado para a sequência: %.2f segundos. (Ainda não é MTM)", totalObservedTime));
        } else {
            updateStatus("Análise de MTM em vídeo é um recurso avançado. Implementação pendente para classificação de movimentos.");
        }
        // Ao final da análise, você adicionaria as atividades calculadas à tabela principal
        // através de um callback ou método estático, similar ao login.
    }

    /**
     * Atualiza o status na interface.
     * @param status A mensagem de status.
     */
    private void updateStatus(String status) {
        currentStatusLabel.setText("Status: " + status);
    }

    public void show() {
        stage.show();
    }

    /**
     * Classe interna para armazenar eventos marcados.
     */
    private static class MarkedEvent {
        String eventType;
        double timeInSeconds;

        public MarkedEvent(String eventType, double timeInSeconds) {
            this.eventType = eventType;
            this.timeInSeconds = timeInSeconds;
        }

        @Override
        public String toString() {
            return eventType + ": " + String.format("%.2f s", timeInSeconds);
        }
    }
}
