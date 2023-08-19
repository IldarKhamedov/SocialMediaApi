package ru.khamedov.ildar.socialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileDTO {

    private String name;

    private String email;

    private String password;
}
