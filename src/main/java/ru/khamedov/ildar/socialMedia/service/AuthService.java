package ru.khamedov.ildar.socialMedia.service;

import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.repository.UserProfileRepository;
import ru.khamedov.ildar.socialMedia.util.Constant;

public class AuthService {

    @Resource
    private UserProfileRepository userProfileRepository;

    public UserProfile getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name;
        if (authentication instanceof JwtAuthenticationToken) {
            name = ((Jwt) authentication.getPrincipal()).getClaim(Constant.USER_NAME);
        } else {
            name = authentication.getName();
        }
        return getUserByName(name);
    }

    public UserProfile getUserByName (String userName){
        return userProfileRepository.findById(userName).get();
    }
}
