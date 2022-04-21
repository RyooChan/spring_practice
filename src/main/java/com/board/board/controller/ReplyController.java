package com.board.board.controller;

import com.board.board.domain.Reply;
import com.board.board.dto.reply.ReplyPostDto;
import com.board.board.dto.reply.ReplySaveDto;
import com.board.board.mapper.Reply.ReplyPostMapper;
import com.board.board.mapper.Reply.ReplySaveMapper;
import com.board.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyPostMapper replyPostMapper;
    private final ReplySaveMapper replySaveMapper;

    @GetMapping("/reply")
    public List<ReplyPostDto> findReplyList(@RequestParam("boardId") Long boardId){
        List<Reply> replyList = replyService.findReplyList(boardId);
        return replyPostMapper.toDtos(replyList);
    }

//    @PostMapping("/reply")
//    public ResponseEntity<ReplyPostDto> saveReply(@RequestParam("reply") ReplySaveDto replySaveDto){
//        Reply reply = replySaveMapper.toEntity(replySaveDto);
//        Reply result = replyService.saveReply(reply);
//
//    }

}
