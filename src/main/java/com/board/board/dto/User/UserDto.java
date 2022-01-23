package com.board.board.dto.User;

import com.board.board.domain.Board;
import com.board.board.domain.Role2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String nickname;
    private String phone;
    private List<Role2> roles = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
}
