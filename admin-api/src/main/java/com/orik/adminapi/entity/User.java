package com.orik.adminapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_ref", referencedColumnName = "role_id")
    @JsonManagedReference(value = "userRole")
    private Role role;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    private List<Message> messagesFromUser;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    private List<Message> messagesToUser;

}


