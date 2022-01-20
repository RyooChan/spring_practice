package com.board.board.dto.Board;

import com.board.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardSaveDto {
    private Long id;
    @Size(min=2, max=30)
    private String title;
    private String content;
//    private String nickname;
//    private User user;

}
