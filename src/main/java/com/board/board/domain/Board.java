package com.board.board.domain;

import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Lob
    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    private boolean isDeleted = false;

    // 삭제 CASCADE를 위함
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    private List<Reply> replys = new ArrayList<>();
//
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    private List<Heart> hearts = new ArrayList<>();

    public Board(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Board() {

    }
}
