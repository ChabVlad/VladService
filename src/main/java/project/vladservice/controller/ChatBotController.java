package project.vladservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.vladservice.bot.ChatBot;
import project.vladservice.util.LogUtils;

@RestController
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBot bot;

    @GetMapping("/chat")
    public String chat(@RequestParam String question) {

        LogUtils.logRequest(question);
        String response = bot.handleUserInput(question);

        LogUtils.logResponse(response);

        return response;
    }
}