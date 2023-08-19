package ru.khamedov.ildar.socialMedia.util.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;

public class AuthService {

    @Resource
    private UserProfileRepository userProfileRepository;

    @Value("${spring.security.jwt.user.name}")
    private String userName;

    public UserProfile getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name;
        if (authentication instanceof JwtAuthenticationToken) {
            name = ((Jwt) authentication.getPrincipal()).getClaim(userName);
        } else {
            name = authentication.getName();
        }
        return userProfileRepository.findById(name).get();
    }
}
