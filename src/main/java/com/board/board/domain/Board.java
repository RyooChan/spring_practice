package com.board.board.domain;

import com.board.board.domain.oauth.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
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

    @Length(min=20)
    @Lob
    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
