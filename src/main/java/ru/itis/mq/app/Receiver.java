package ru.itis.mq.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.mq.app.models.MessageLog;
import ru.itis.mq.app.repositories.MessageRepository;

import java.io.IOException;

@Component
public class Receiver {

    private ObjectMapper objectMapper;

    @Autowired
    public Receiver(ObjectMapper objectMapper, MessageRepository messageRepository) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
    }
    private MessageRepository messageRepository;

    @RabbitListener(queues = "demo-queue")
    public void process(byte[] messageAsBytes) {
        String jsonMessage = new String(messageAsBytes);
        try {
            Message message = objectMapper.readValue(jsonMessage, Message.class);
            MessageLog ml = MessageLog.builder()
                    .name(message.getName())
                    .email(message.getEmail())
                    .message(message.getMessage())
                    .phoneNumber(message.getPhoneNumber())
                    .build();
            messageRepository.save(ml);
            System.out.println(message);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
