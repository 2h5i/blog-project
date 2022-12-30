package com.sparta.blogproject.User.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

//    @Column(nullable = false)
//    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    List<Comment> commentList = new ArrayList<>();

    public User(String username, String password, UserRoleEnum userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
