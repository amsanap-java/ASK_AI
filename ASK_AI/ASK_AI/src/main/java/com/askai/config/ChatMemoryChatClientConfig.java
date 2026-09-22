package com.askai.config;

import com.askai.advisors.TokenUsageAuditAdvisor;
import com.askai.rag.PIIMaskingDocumentPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {

    @Bean("chatMemoryClient")
    public ChatClient chatclient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
         Advisor loggerAdvisor= new SimpleLoggerAdvisor();
         Advisor tokenUsageAdvisor=new TokenUsageAuditAdvisor();
         Advisor memoryadvisor=  MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder.defaultAdvisors(List.of(loggerAdvisor,memoryadvisor,tokenUsageAdvisor,retrievalAugmentationAdvisor)).build();
    }

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore,
                                                              ChatClient.Builder chatClientBuilder) {
        return RetrievalAugmentationAdvisor.builder()
                .queryTransformers(TranslationQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder.clone())
                        .targetLanguage("english").build())
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(3).similarityThreshold(0.5).build())
                .documentPostProcessors(PIIMaskingDocumentPostProcessor.builder())
                .build();
    }
}


