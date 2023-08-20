package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.post.ImageContent;

public interface ImageContentRepository extends JpaRepository<ImageContent,Long> {
}
