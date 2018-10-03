package ru.itis.mq.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.mq.app.models.MessageLog;

public interface MessageRepository extends JpaRepository<MessageLog, Long> {
}
