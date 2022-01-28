package com.board.board.domain;

import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
public class Board {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String title;

    @NotBlank
    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    private String nickname;

    // test4
//    private long userId;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}
