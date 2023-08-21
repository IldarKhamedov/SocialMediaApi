package ru.khamedov.ildar.socialMedia.model.communication;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends Sending {

    @Lob
    @Basic(optional = false)
    @JdbcTypeCode(Types.LONGNVARCHAR)
    private String text;

}
