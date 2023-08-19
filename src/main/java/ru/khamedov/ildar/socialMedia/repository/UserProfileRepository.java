package ru.khamedov.ildar.socialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khamedov.ildar.socialMedia.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

}
