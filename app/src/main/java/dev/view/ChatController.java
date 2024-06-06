package dev.view;

import dev.service.ChatService;
import dev.langchain4j.service.TokenStream;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.concurrent.atomic.AtomicReference;

public class ChatController {
    private final ChatService chatService; // ChatService 인스턴스
    private final VBox chatArea; // 채팅 영역을 나타내는 VBox
    private final Label typingIndicator; // 타이핑 인디케이터 라벨
    private final ScrollPane scrollPane; // 스크롤 가능한 영역

    // 생성자: ChatService와 UI 요소를 초기화
    public ChatController(String ollamaHost, VBox chatArea, Label typingIndicator, ScrollPane scrollPane) {
        this.chatService = new ChatService(ollamaHost, "aya:latest"); // ChatService 초기화
        this.chatArea = chatArea; // 채팅 영역 초기화
        this.typingIndicator = typingIndicator; // 타이핑 인디케이터 초기화
        this.scrollPane = scrollPane; // 스크롤 가능한 영역 초기화
    }

    // 사용자 입력을 처리하는 메서드
    public void handleUserInput(String userText) {
        // 사용자 메시지를 UI에 추가
        addMessage("You: " + userText, Pos.CENTER_RIGHT);
        typingIndicator.setVisible(true); // 타이핑 인디케이터 표시

        // 봇 응답 텍스트를 업데이트할 Label 생성
        Label botMessageLabel = new Label();
        botMessageLabel.setWrapText(true); // 텍스트 줄바꿈 설정
        botMessageLabel.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10px; -fx-padding: 10px;"); // 스타일 설정
        botMessageLabel.setMaxWidth(500); // 최대 너비 설정

        // 봇 응답을 담을 HBox 생성
        HBox botMessageBox = new HBox(10); // 10 픽셀 간격의 HBox
        botMessageBox.setAlignment(Pos.CENTER_LEFT); // 좌측 정렬
        botMessageBox.getChildren().add(botMessageLabel); // HBox에 Label 추가
        chatArea.getChildren().add(botMessageBox); // 채팅 영역에 HBox 추가

        // 응답 문자열을 저장할 AtomicReference 생성
        AtomicReference<String> botResponse = new AtomicReference<>("");

        // 모델과 통신하여 실시간으로 응답을 처리
        TokenStream tokenStream = chatService.getTokenStream(userText);
        tokenStream.onNext(token -> {
            // 각 토큰을 받을 때마다 호출되는 콜백
            Platform.runLater(() -> {
                botResponse.set(botResponse.get() + token.toString()); // 응답 문자열에 토큰 추가
                botMessageLabel.setText(botResponse.get()); // Label을 업데이트하여 UI에 표시
            });
        }).onComplete(_ -> {
            // 응답이 완료되면 호출되는 콜백
            Platform.runLater(() -> {
                typingIndicator.setVisible(false); // 응답 완료 후 타이핑 인디케이터 숨김
            });
        }).onError(Throwable::printStackTrace).start(); // 오류 발생 시 스택 트레이스 출력 및 스트림 처리 시작

        // 스크롤을 최신 메시지로 이동
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }

    // 메시지를 UI에 추가하는 메서드
    private void addMessage(String text, Pos position) {
        Label messageLabel = new Label(text); // 메시지 라벨 생성
        messageLabel.setWrapText(true); // 텍스트 줄바꿈 설정
        messageLabel.setStyle("-fx-background-color: lightblue; -fx-background-radius: 10px; -fx-padding: 10px;"); // 스타일 설정
        messageLabel.setMaxWidth(500); // 최대 너비 설정

        HBox messageBox = new HBox(10); // 10 픽셀 간격의 HBox
        messageBox.setAlignment(position); // 위치 설정
        messageBox.getChildren().add(messageLabel); // HBox에 Label 추가
        chatArea.getChildren().add(messageBox); // 채팅 영역에 HBox 추가

        // 스크롤을 최신 메시지로 이동
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}
