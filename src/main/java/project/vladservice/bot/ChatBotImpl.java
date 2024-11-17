package project.vladservice.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.vladservice.openai.OpenAIIntegration;

@Component
@RequiredArgsConstructor
public class ChatBotImpl implements ChatBot {
    private final OpenAIIntegration integration;

    public String handleUserInput(String userInput) {
        return getAnswerFromFineTunedModel(userInput);
    }

    private String getAnswerFromFineTunedModel(String question) {
        return integration.getResponse(question);
    }
}
