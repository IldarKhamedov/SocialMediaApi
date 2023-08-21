package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.communication.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
