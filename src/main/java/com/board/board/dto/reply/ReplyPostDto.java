package com.board.board.dto.reply;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyPostDto {
    long id;

    String replyContent;

    String userName;

    long userId;

    long boardId;

    boolean checkUser;
}