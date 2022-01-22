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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@RequiredArgsConstructor
public class BoardService {

//     RequiredArgsConstructor어노테이션을 활용하여 생성자 주입.
//     autowired를 사용한 필드주입의 경우 외부에서 변경이 불가능하고, DI프레임워크가 필수적으로 필요하다.
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final BoardSaveMapper boardSaveMapper;

    private final BoardPostMapper boardPostMapper;

    // 뀨 안됨
    public Model paging(Model model, Pageable pageable, String searchText){
        Page<BoardPostDto> boards = (Page<BoardPostDto>) boardPostMapper.toDto((Board)boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable));
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);

        return model;
    }

    @Transactional
    public BoardSaveDto save(String username, BoardSaveDto boardSaveDto){
        User user = userRepository.findByUsername(username);    // user의 정보를 작성자 정보를 통해 받아온다.
        Board board = boardSaveMapper.toEntity(boardSaveDto);           // mapstruct를 사용하여 Dto의 정보를 entity로 바꾸어준다.
        boardSaveMapper.updateFromDto(boardSaveDto, board);             // null인 값들을 빼주기 위한 updateFromDto 적용
        board.setUser(user);                                    // entity에 user정보를 적용해준다.

        return boardSaveMapper.toDto(boardRepository.save(board));
    }

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
