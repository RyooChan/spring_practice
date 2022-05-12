package com.board.board.domain;

import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Lob
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId", referencedColumnName = "id")
    private Board board;

    private boolean isDeleted = false;
}