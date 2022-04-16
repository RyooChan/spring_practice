package com.board.board.dto.Board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardListDto {
    private Long id;
    private String title;
    private String userName;

    // QClass projection
    @QueryProjection
    public BoardListDto(Long id, String title, String userName) {
        this.id = id;
        this.title = title;
        this.userName = userName;
    }
}
