package com.board.board.dto.Board;

import com.board.board.domain.oauth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardPostDto {
    private Long id;
    @Size(min=2, max=30)
    private String title;
    @NotBlank(message = "내용을 입력해 주세요~")
    private String content;

    private User user;
}
