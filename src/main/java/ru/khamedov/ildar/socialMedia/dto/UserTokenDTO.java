package ru.khamedov.ildar.socialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserTokenDTO {

    private String name;

    private String password;
}
