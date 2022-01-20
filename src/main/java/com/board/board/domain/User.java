package com.board.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // DB와의 연결을 위하여
@Data   // getter setter
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@RequiredArgsConstructor
public class User {
    @Id // id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private String nickname;

    private String phone;

    @JsonIgnore     // api에서 JSON검색할 때 유저의 role같은걸 가져오면 안돼서 ignore시켜준다.
    @ManyToMany
    @JoinTable(
            name = "user_role" ,
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false) // mapped 사용해서 Board에서 manyToOne 으로 구한 user를 양방향 매핑하게 한다.
    // cascade : ALL로 설정해서 해당 유저가 삭제되면 관련 데이터도 다 삭제할 수 있도록
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)  // eager는 사용자 조회시에 바로 가져올거 / lazy는 필요할 때에 가져올것이다.
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
