package ru.khamedov.ildar.socialMedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Аутентификация и авторизация")
public class UserRestController {

    @Resource
    private UserProfileService userProfileService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/register")
    @Operation(summary = "Пользователи могут зарегистрироваться, указав имя пользователя, электронную почту и пароль")
    public ResponseEntity<?> create(@RequestBody UserProfileDTO userProfileDTO) {
        UserProfile userProfile = userProfileService.createUser(userProfileDTO);
        String token = tokenService.createToken(userProfile);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/token")
    @Operation(summary = "Получение токена")
    public ResponseEntity token(@RequestBody UserTokenDTO userTokenDTO) {
        UserProfile userProfile=userProfileService.getUserForToken(userTokenDTO);
        String token = tokenService.createToken(userProfile);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
