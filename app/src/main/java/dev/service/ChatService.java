package dev.service;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class ChatService implements UserStreamCommunication, ModelCommunication {

    private final StreamingChatLanguageModel languageModel;
    private final ModelCommunication assistant;

    // 생성자: 모델 URL과 이름을 받아 모델과의 연결을 설정하고 초기화
    public ChatService(String modelUrl, String modelName) {
        this.languageModel = connectModel(modelUrl, modelName);
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        this.assistant = AiServices.builder(ModelCommunication.class)
                .streamingChatLanguageModel(this.languageModel)
                .chatMemory(chatMemory)
                .build();
    }

    // 사용자 질문을 비동기적으로 처리하는 메서드
    @Override
    public CompletableFuture<Void> ask(String userPrompt) {
        // 모델과의 통신을 시작하고, 응답 스트림을 받음
        TokenStream tokenStream = chatWithModel(userPrompt);
        
        // CompletableFuture를 생성하여 비동기 작업을 처리할 준비를 함
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        // 모델 응답 토큰을 받을 때마다 실행할 콜백 설정
        tokenStream.onNext(System.out::print) // 각 토큰을 콘솔에 출력
            .onComplete(_ -> { // 응답이 완료되면 실행
                System.out.println();
                future.complete(null); // CompletableFuture를 완료 상태로 설정
            })
            .onError(Throwable::printStackTrace) // 오류가 발생하면 스택 트레이스를 출력
            .start(); // 스트림 처리를 시작

        return future; // 비동기 작업의 완료 상태를 반환
    }

    // 모델과의 통신을 수행하고 TokenStream을 반환하는 메서드
    @Override
    public TokenStream chatWithModel(String message) {
        return assistant.chatWithModel(message);
    }

    public TokenStream getTokenStream(String userPrompt) {
        return chatWithModel(userPrompt);
    }

    // 모델과의 연결을 설정하는 정적 메서드
    private static StreamingChatLanguageModel connectModel(String url, String modelName) {
        return OllamaStreamingChatModel.builder()
                .baseUrl(url)
                .modelName(modelName)
                .timeout(Duration.ofHours(1))
                .build();
    }
}
