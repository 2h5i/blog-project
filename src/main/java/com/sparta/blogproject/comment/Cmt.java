package com.sparta.blogproject.comment;


import com.sparta.blogproject.common.entity.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Cmt extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comments;

    public Cmt(String username, String comments) {
        this.username = username;
        this.comments = comments;
    }

    public Cmt(CmtDto requestDto) {
        this.username = requestDto.getUsername();
        this.comments = requestDto.getComments();
    }

    public void update(CmtDto requestDto) {
        this.username = requestDto.getUsername();
        this.comments = requestDto.getComments();
    }
}
