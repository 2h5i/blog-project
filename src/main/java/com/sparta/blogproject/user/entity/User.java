package com.sparta.blogproject.user.entity;

import com.sparta.blogproject.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Post> postList = new ArrayList<>();

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
//    List<Comment> commentList = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = userRole;
    }
}
