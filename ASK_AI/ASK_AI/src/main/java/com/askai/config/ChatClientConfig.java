package com.askai.config;

import com.askai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
       ChatOptions chatOptions= ChatOptions.builder().model("gpt-3.5-turbo").maxTokens(100).temperature(0.5).build();
        return  chatClientBuilder.
                defaultOptions(chatOptions).
                defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor(),new SimpleLoggerAdvisor()))
             // defaultAdvisors(new TokenUsageAuditAdvisor()).
              .defaultSystem("""
                you are an internal " +
                "HR asistant for a company. You will answer questions related to HR policies,
                 benefits, and employee services. """).defaultUser("how can you help me?")
              .defaultUser("How i can you help me?").build();


    }
}

