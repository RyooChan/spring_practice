package com.board.board.domain;

import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
public class Reply {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Lob
    @Size(min = 10, max = 500)
    private String replyContent;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="boardId", referencedColumnName = "id")
    @JsonIgnore
    private Board board;
}
