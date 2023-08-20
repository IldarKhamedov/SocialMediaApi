package ru.khamedov.ildar.socialMedia.model.communication;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.khamedov.ildar.socialMedia.model.UserProfile;

import java.sql.Types;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends AbstractPersistable<Long> {

    @Lob
    @Basic(optional = false)
    @JdbcTypeCode(Types.LONGNVARCHAR)
    private String text;

    @ManyToOne(optional = false)
    private UserProfile sender;

    @ManyToOne(optional = false)
    private UserProfile receiver;

    private boolean read;

    private Instant created= Instant.now();
}
