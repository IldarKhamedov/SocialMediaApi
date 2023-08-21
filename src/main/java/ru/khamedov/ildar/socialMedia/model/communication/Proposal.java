package ru.khamedov.ildar.socialMedia.model.communication;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proposal extends Sending {

    private boolean activeBySender=true;

    private boolean approvedByReceiver;
}
