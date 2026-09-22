package com.askai.controller;

import com.askai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat-structured")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("message") String message) {
        CountryCities countryCites = chatClient
                .prompt()
                .user(message)
                .call().entity(CountryCities.class);
        return ResponseEntity.ok(countryCites);
    }

    @GetMapping("/chat-List")
    public ResponseEntity<List<String>> chatList(@RequestParam("message") String message) {
        List<String> countryCites = chatClient
                .prompt()
                .user(message)
                .call().entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCites);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String,Object>> chatMap(@RequestParam("message") String message) {
        Map<String, Object> countryCities = chatClient
                .prompt()
                .user(message)
                .call().entity(new MapOutputConverter());
        return ResponseEntity.ok(countryCities);
    }
    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("message") String message) {
        List<CountryCities> countryCities = chatClient
                .prompt()
                .user(message)
                .call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(countryCities);
    }


}