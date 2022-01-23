package com.board.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
public class Role2 {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // 이걸 사용해서 JSON으로 API를 불러올때 재귀를 없애줄 수 있다. 이 role을 갖고있는 사용자는 표시하지 않을 것이다.
    private List<User2> users;
}
