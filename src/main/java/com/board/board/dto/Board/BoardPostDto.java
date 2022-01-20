package com.board.board.dto.Board;

import com.board.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardPostDto {
    private Long id;
    private String title;
    private String content;
}
