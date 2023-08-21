package ru.khamedov.ildar.socialMedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Пользователи передают имя пользователя и пароль для получения токена")
public class UserTokenDTO {

    private String name;

    private String password;
}
