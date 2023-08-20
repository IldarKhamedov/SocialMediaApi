package ru.khamedov.ildar.socialMedia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfile  {

    @Id
    private String name;

    @Basic(optional = false)
    private String email;

    @Basic(optional = false)
    private String password;

    @ManyToMany
    private Set<UserProfile> friends=new HashSet<>();

    @ManyToMany
    private Set<UserProfile> subscribers=new HashSet<>();

}
