package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.domain.oauth.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

//     RequiredArgsConstructor어노테이션을 활용하여 생성자 주입.
//     autowired를 사용한 필드주입의 경우 외부에서 변경이 불가능하고, DI프레임워크가 필수적으로 필요하다.
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    public List<Board> list(String searchText){
        return boardRepository.findByTitleContainingOrContentContaining(searchText, searchText);
    }

    @Transactional
    public void save(String userEmail, Board board){
        User user = userRepository.findUserByEmail(userEmail);    // user의 정보를 작성자 정보를 통해 받아온다.
        board.setUser(user);                                    // entity에 user정보를 적용해준다.
        boardRepository.save(board);
    }

    public Board post(Long id){
        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public Board postForm(Long id){
        return boardRepository.findById(id).orElse(null);
    }

//    public Board post(Long id){
//        Board board = boardRepository.findById(id).orElse(null);
//
//        return 1;
//    }
}
