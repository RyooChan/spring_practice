package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.domain.Reply;
import com.board.board.domain.oauth.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.ReplyRepository;
import com.board.board.repository.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class BoardService {

//     RequiredArgsConstructor어노테이션을 활용하여 생성자 주입.
//     autowired를 사용한 필드주입의 경우 외부에서 변경이 불가능하고, DI프레임워크가 필수적으로 필요하다.
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ReplyRepository replyRepository;

    public List<Board> list(String searchText){
        return boardRepository.findByTitleContainingOrContentContaining(searchText, searchText);
    }

    // 작성자 확인
    // param : 작성글Id, 로그인 Email
    // 작성글의 Id를 통해 관련 user의 정보를 가져온 후, 로그인한 Id와 Email이 일치하는지 검사한다.
    // 일치하면 True, 다르면 False return
    @Transactional
    public boolean confirm(long boardId, String sessionEmail){
        Board board = boardRepository.findByUserId(boardId);
        String boardUserEmail = board.getUser().getEmail();
        return (Objects.equals(boardUserEmail, sessionEmail));
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

    public List<Reply> getReply(Long boardId){
        return replyRepository.findByBoardId(boardId);
    }

//    public Board post(Long id){
//        Board board = boardRepository.findById(id).orElse(null);
//
//        return 1;
//    }
}
