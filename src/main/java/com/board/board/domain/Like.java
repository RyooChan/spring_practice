package com.board.board.domain;

import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
public class Like {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;  // 좋아요 누른 user

    @ManyToOne
    @JoinColumn(name="board_id", referencedColumnName = "id")
    @JsonIgnore
    private Board board;    // 해당 글
}
