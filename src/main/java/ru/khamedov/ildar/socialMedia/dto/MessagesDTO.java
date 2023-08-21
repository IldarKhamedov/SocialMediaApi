package ru.khamedov.ildar.socialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDTO {

    private String sender;

    private String receiver;

    private Date created;
}
