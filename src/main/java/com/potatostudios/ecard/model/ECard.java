package com.potatostudios.ecard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class ECard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // 카드 ID (랜덤 UUID)

    @Column(nullable = false)
    private String message;  // 사용자 입력 메시지
    private String eventDate;
    private String location;
    private String senderName;
    private String senderEmail;
    private String receiverName;
    private String receiverEmail;


    @Transient  // 이 필드는 DB에 저장되지 않도록 설정
    public String getShareableLink() {
        return "http://localhost:8080/ecard/" + id;
    }
}
