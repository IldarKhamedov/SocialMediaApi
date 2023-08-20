package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile,Long> {
}
