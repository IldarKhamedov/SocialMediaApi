package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.khamedov.ildar.socialMedia.model.communication.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("SELECT m FROM Message m WHERE " +
            " m.sender.name=:userNameOne AND m.receiver.name=:userNameTwo " +
            " OR " +
            " m.sender.name=:userNameTwo AND m.receiver.name=:userNameOne " +
            " ORDER BY m.created DESC")
    List<Message> findByUserName(@Param("userNameOne")String userNameOne, @Param("userNameTwo")String userNameTwo);

}
