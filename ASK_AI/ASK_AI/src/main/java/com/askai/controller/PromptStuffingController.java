package com.askai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PromptStuffingController {

    @Value("classpath:/promptTemplate/systemPromptTemplate.st")
    Resource systemPromptTemplate;
    private ChatClient chatClient;
    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @GetMapping("/prompt-stuffing")
    public String emailResponse(@RequestParam("message") String message
    ) {
        return chatClient
                .prompt()
                .options(OpenAiChatOptions.builder().model("gpt-3.5-turbo").temperature(0.7).maxTokens(150).build())
                .system(systemPromptTemplate)
                .user(message)
                .call().content();
    }

}

