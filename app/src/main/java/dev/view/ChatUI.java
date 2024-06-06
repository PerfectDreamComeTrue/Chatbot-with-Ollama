package dev.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatUI extends Application {

    private ChatController controller;
    private VBox chatArea;
    private Label typingIndicator;
    private TextArea userInput;

    @Override
    public void start(Stage stage) {
        // 최상위 레이아웃 설정
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
    
        // 모델 정보 라벨 설정
        Label modelInfo = new Label("aya:latest");
    
        // 타이핑 인디케이터 설정
        typingIndicator = new Label("답변을 작성 중입니다...");
        typingIndicator.setVisible(false);
        HBox typingContainer = new HBox(typingIndicator);
        typingContainer.setAlignment(Pos.CENTER_RIGHT);
    
        // 채팅 영역 설정
        chatArea = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    
        // 사용자 입력 필드 설정
        userInput = new TextArea();
        userInput.setPromptText("Enter your message here...");
        userInput.setPrefHeight(30);
        userInput.setMaxHeight(300);
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            adjustTextAreaHeight(userInput);
        });
        userInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                String userText = userInput.getText();
                if (!userText.trim().isEmpty()) {
                    controller.handleUserInput(userText.trim());
                    userInput.clear();
                    adjustTextAreaHeight(userInput);
                    event.consume();
                }
            }
        });
    
        // 레이아웃 구성 요소 추가
        root.setTop(new VBox(modelInfo, typingContainer));
        root.setCenter(scrollPane);
        root.setBottom(userInput);
    
        // 씬 설정 및 스테이지 표시
        Scene scene = new Scene(root, 1400, 982);
        stage.setTitle("Chatbot");
        stage.setScene(scene);
        stage.show();
    
        // 컨트롤러 초기화
        controller = new ChatController("http://localhost:11434", chatArea, typingIndicator, scrollPane);
    }
    

    private void adjustTextAreaHeight(TextArea textArea) {
        Platform.runLater(() -> {
            double textHeight = computeTextHeight(textArea);
            double newHeight = Math.min(300, textHeight + 24);
            textArea.setPrefHeight(newHeight);
            textArea.setMaxHeight(300);
            textArea.setMinHeight(30);

            textArea.applyCss();
            textArea.layout();
            if (textArea.getParent() != null) {
                textArea.getParent().applyCss();
                textArea.getParent().layout();
            }
        });
    }

    private double computeTextHeight(TextArea textArea) {
        Text text = new Text(textArea.getText());
        text.setFont(textArea.getFont());
        double textHeight = text.getLayoutBounds().getHeight();
        return textHeight + 24;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
