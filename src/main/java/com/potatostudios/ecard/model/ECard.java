package com.potatostudios.ecard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ecards")
@Getter
@Setter
public class ECard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String recipient;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 외래 키(FK) 설정
    private User user;

    public ECard() {}

    public ECard(String title, String message, String sender, String recipient, User user) {
        this.title = title;
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        this.user = user;
    }
}