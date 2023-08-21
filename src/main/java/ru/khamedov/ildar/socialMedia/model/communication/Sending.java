package ru.khamedov.ildar.socialMedia.model.communication;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.khamedov.ildar.socialMedia.model.UserProfile;

import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Sending extends AbstractPersistable<Long> {

    @ManyToOne(optional = false)
    private UserProfile sender;

    @ManyToOne(optional = false)
    private UserProfile receiver;

    private Instant created= Instant.now();

    private boolean processed;
}
