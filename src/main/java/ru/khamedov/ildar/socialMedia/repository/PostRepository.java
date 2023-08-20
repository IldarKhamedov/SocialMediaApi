package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.post.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
