package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.domain.User;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.dto.Board.BoardSaveDto;
import com.board.board.mapper.Board.BoardPostMapper;
import com.board.board.mapper.Board.BoardSaveMapper;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    private final BoardSaveMapper boardSaveMapper;

    private final BoardPostMapper boardPostMapper;

    @Transactional
    public BoardSaveDto save(String username, BoardSaveDto boardSaveDto){
        User user = userRepository.findByUsername(username);    // user의 정보를 작성자 정보를 통해 받아온다.
        Board board = boardSaveMapper.toEntity(boardSaveDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
        boardSaveMapper.updateFromDto(boardSaveDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용
        board.setUser(user);                                    // entity에 user정보를 적용해준다.

        return boardSaveMapper.toDto(boardRepository.save(board));
    }

    @Transactional
    public BoardPostDto post(Long id){
        Board board = boardRepository.findById(id).orElse(null);
        BoardPostDto boardPostDto = boardPostMapper.toDto(board);
        boardPostMapper.updateFromDto(boardPostDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용

        return boardPostDto;
    }

    @Transactional
    public BoardSaveDto postForm(Long id){
        Board board = boardRepository.findById(id).orElse(null);
        BoardSaveDto boardSaveDto = boardSaveMapper.toDto(board);
        boardSaveMapper.updateFromDto(boardSaveDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용

        return boardSaveDto;
    }

//    public Board post(Long id){
//        Board board = boardRepository.findById(id).orElse(null);
//
//        return 1;
//    }
}
