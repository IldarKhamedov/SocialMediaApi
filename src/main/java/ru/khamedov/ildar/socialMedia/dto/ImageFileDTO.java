package ru.khamedov.ildar.socialMedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khamedov.ildar.socialMedia.model.post.ImageContent;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Картинки для добавления к посту")
public class ImageFileDTO {

    private ImageContentDTO imageContentDTO;

    private Long length;

    private String contentType;
}
