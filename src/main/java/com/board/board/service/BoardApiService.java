package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.mapper.Board.BoardSaveMapper;
import com.board.board.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardApiService {

    private final BoardRepository boardRepository;

    // 저장용 DTO BoardSaveDto와의 DTO-Entity간 변경을 위한 mapper
    private final BoardSaveMapper boardSaveMapper;

    // 출력용 DTO BoardPostDto와의 DTO-Entity간 변경을 위한 mapper
    private final BoardPostMapper boardPostMapper;

    // list를 받아서 출력할 수 있도록 해야한다?
    // 변경예정 지금은 안됨
    public List<BoardPostDto> findBoardApi(String title, String content){
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return (List<BoardPostDto>) boardPostMapper.toDto((Board) boardRepository.findAll());
        }else{
            return (List<BoardPostDto>) boardPostMapper.toDto((Board) boardRepository.findByTitleOrContent(title, content));
        }
    }

}
