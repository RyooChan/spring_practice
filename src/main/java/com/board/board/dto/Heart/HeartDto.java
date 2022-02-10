package com.board.board.dto.Heart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeartDto {
    long id;

    long userId;

    long boardId;
}
