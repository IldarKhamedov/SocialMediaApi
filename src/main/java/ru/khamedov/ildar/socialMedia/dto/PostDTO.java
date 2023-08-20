package ru.khamedov.ildar.socialMedia.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String title;

    private String text;

    private Date created;

    private List<ImageFileDTO> imageFileDTOList;
}
