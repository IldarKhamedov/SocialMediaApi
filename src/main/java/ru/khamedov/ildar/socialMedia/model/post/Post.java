package ru.khamedov.ildar.socialMedia.model.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.khamedov.ildar.socialMedia.model.UserProfile;

import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractPersistable<Long> {

    @ManyToOne(optional = false)
    private UserProfile author;

    @Basic(optional = false)
    private String title;

    @Lob
    @Basic(optional = false)
    @JdbcTypeCode(Types.LONGNVARCHAR)
    private String text;

    private Instant created= Instant.now();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ImageFile> imageFileList=new ArrayList<>();
}
