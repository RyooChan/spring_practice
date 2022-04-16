package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.domain.Heart;
import com.board.board.domain.Reply;
import com.board.board.domain.oauth.User;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.HeartRepository;
import com.board.board.repository.ReplyRepository;
import com.board.board.repository.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class BoardService2 {

//     RequiredArgsConstructor어노테이션을 활용하여 생성자 주입.
//     autowired를 사용한 필드주입의 경우 외부에서 변경이 불가능하고, DI프레임워크가 필수적으로 필요하다.
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ReplyRepository replyRepository;

    private final HeartRepository heartRepository;

//    public List<Board> list(String searchText){
    public List<Board> list(String searchText){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        return boardRepository.findByTitleContainingOrContentContaining(searchText, searchText);
        return boardRepository.findAllByTitleContainingOrContentContaining(searchText, searchText, sort);
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
        return replyRepository.findAllByBoardId(boardId);
    }

    public List<Heart> getLikeAll(Long boardId){
        return heartRepository.findAllByBoardId(boardId);
    }

    public Long getHeartCount(Long boardId){
        return heartRepository.countByBoardId(boardId);
    }

    // 나의 좋아요 여부, 내가 한 좋아요 Id를 가져온다.
    // 내가 좋아요 한 정보를 모두 가져온다. 이는 나중에
    public Heart getMyHeart(Long boardId, Long userId){
        return heartRepository.findByBoardIdAndUserId(boardId, userId);
    }

    // 좋아요 저장하기.
    // 좋아요 정보 : boardId, userId를 받아 저장한다.
    public void saveHeart(Heart heart){
        heartRepository.save(heart);
    }

    // 좋아요 지우기
    // 좋아요 id를 받아와 이를 삭제한다.
    public void deleteHeart(long id){
        heartRepository.deleteById(id);
    }

    public void deleteBoard(long id){
        boardRepository.deleteById(id);
    }

    public void deleteReply(long id){
        replyRepository.deleteById(id);
    }

    // 댓글 작성자 확인
    // param : 작성글Id, 로그인 id
    // 작성글의 Id를 통해 관련 user의 정보를 가져온 후, 로그인한 Id와 id가 일치하는지 검사한다.
    // 일치하면 True, 다르면 False return
    @Transactional
    public boolean confirmReply(long replyId, long sessionId){
        Reply reply = replyRepository.findById(replyId);
        long replyUserId = reply.getUser().getId();
        return ( replyUserId == sessionId );
    }

    // 댓글 저장
    // param : 작성자 id, 댓글 내용
    // 작성자를 찾아서 댓글을 등록함.
    @Transactional
    public Reply saveReply(long userId, Reply reply){
        User user = userRepository.findUserById(userId);    // user의 정보를 작성자 정보를 통해 받아온다.
        reply.setUser(user);                                    // entity에 user정보를 적용해준다.
        return replyRepository.save(reply);
    }

    public List<Reply> outReply(long boardId){
        return replyRepository.findAllByBoardId(boardId);
    }
}
