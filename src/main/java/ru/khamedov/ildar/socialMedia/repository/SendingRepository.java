package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.communication.Sending;

public interface SendingRepository extends JpaRepository<Sending,Long> {
}
