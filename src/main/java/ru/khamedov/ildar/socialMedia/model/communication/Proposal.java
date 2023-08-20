package ru.khamedov.ildar.socialMedia.model.communication;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.khamedov.ildar.socialMedia.model.UserProfile;

import java.time.Instant;

@Entity
public class Proposal extends AbstractPersistable<Long> {

    @ManyToOne(optional = false)
    private UserProfile sender;

    @ManyToOne(optional = false)
    private UserProfile receiver;

    private Instant created= Instant.now();

    private boolean activeBySender=true;

    private boolean approvedByReceiver;

    private boolean applied;
}
