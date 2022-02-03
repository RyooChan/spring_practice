package com.board.board.dto.reply;

import com.board.board.domain.Board;
import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDto {
    long id;

    @NotBlank
//    @Lob
    String content;

    long user_id;
}
