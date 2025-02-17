package edu.fpt.customflorist.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class ChatboxAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatSessionID")
    private int chatSessionID;

    @Column(name = "userID")
    private int userID;

    @Column(name = "chatCode")
    private String chatCode;

    @Column(name = "query")
    private String query;

    @Column(name = "response")
    private String response;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
