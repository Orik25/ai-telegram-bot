package com.orik.botapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column
    private String text;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @ManyToOne
    @JoinColumn(name = "from_user_ref")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_ref")
    private User toUser;
}
