package com.board.board.dto.Board;

import com.board.board.domain.oauth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@Data
public class BoardPostDto {
    private Long id;
    @Size(min=2, max=30)
    private String title;

    @Length(min=20)
    @Lob
    private String content;

    private User user;

    public BoardPostDto(){}
}
