package ru.khamedov.ildar.socialMedia.model.post;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.sql.Types;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageContent  extends AbstractPersistable<Long> {

    @Lob
    @JdbcTypeCode(Types.VARBINARY)
    @Basic(optional = false)
    private byte[] content = new byte[0];
}
