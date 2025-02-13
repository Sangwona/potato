package com.potatostudios.ecard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;  // 해싱

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ECard> ecards;

    public User() {
    }

    public User(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }
}