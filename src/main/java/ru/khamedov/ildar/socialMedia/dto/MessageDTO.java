package ru.khamedov.ildar.socialMedia.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Сообщение, которое будет отправлено пользователю")
public class MessageDTO {

    private String text;
}
