package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.post.Post;

import java.time.Instant;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p WHERE p.author.name=:userName")
    List<Post> findPostByUserName(@Param("userName")String userName);

    @Query("SELECT p FROM Post p, UserProfile u" +
            " LEFT JOIN u.subscribers as sub" +
            " WHERE sub=:user " +
            " AND p.author=u "+
            " AND p.created >:created "
    )
    List<Post> findPostByInstant(@Param("user")UserProfile user,@Param("created") Instant created,  Pageable pageable);
}
