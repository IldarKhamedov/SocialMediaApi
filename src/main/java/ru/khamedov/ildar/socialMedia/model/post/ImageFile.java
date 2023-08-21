package ru.khamedov.ildar.socialMedia.model.post;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageFile extends AbstractPersistable<Long> {

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private ImageContent imageContent;

    @Basic(optional = false)
    private long length;

    @Basic(optional = false)
    private String contentType;

    @ManyToOne(optional = false)
    private Post post;
}
