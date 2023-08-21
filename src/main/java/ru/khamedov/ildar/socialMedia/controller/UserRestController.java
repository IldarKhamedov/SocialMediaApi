package ru.khamedov.ildar.socialMedia.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamedov.ildar.socialMedia.dto.UserProfileDTO;
import ru.khamedov.ildar.socialMedia.dto.UserTokenDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.service.TokenService;
import ru.khamedov.ildar.socialMedia.service.UserProfileService;


@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Resource
    private UserProfileService userProfileService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserProfileDTO userProfileDTO) {
        UserProfile userProfile = userProfileService.createUser(userProfileDTO);
        String token = tokenService.createToken(userProfile);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody UserTokenDTO userTokenDTO) {
        UserProfile userProfile=userProfileService.getUserForToken(userTokenDTO);
        String token = tokenService.createToken(userProfile);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
