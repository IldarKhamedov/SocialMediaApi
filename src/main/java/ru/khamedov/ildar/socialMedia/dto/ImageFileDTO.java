package ru.khamedov.ildar.socialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khamedov.ildar.socialMedia.model.post.ImageContent;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileDTO {

    private ImageContentDTO imageContentDTO;

    private Long length;

    private String contentType;
}
