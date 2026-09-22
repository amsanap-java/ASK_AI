CREATE TABLE SPRING_AI_CHAT_MEMORY (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       conversation_id VARCHAR(255) NOT NULL,
                                       content CLOB NOT NULL,
                                       type VARCHAR(50) NOT NULL,
                                       TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX
    ON SPRING_AI_CHAT_MEMORY(conversation_id, TIMESTAMP DESC);

ALTER TABLE SPRING_AI_CHAT_MEMORY
    ADD CONSTRAINT TYPE_CHECK CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));