package com.board.board.service;

import com.board.board.domain.Heart;
import com.board.board.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartService {
    private final HeartRepository heartRepository;

    public List<Heart> findHeartList(Long boardId){
        return heartRepository.findAllByBoardId(boardId);
    }

    public Heart findMyHeart(Long boardId){
        return heartRepository.findById(boardId).orElse(null);
    }

    public Heart doHeart(Heart heart){
        return heartRepository.save(heart);
    }

    public Heart changeHeart(Long boardId, Long userId){
        return heartRepository.findByBoardIdAndUserId(boardId, userId);
    }

}
