package com.board.board.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class BoardListDto {
    private Long id;
    private String title;
    private String userName;
}
