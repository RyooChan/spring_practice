package com.board.board.dto.reply;

import com.board.board.domain.Board;
import com.board.board.domain.oauth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDto {
    long id;

    @NotBlank
    @Lob
    @Size(min = 10, max = 500)
    String replyContent;

    long userId;

    long boardId;
}
