package com.board.board.controller;

import java.io.IOException;
import java.util.List;

import com.board.board.domain.Board;
import com.board.board.dto.Board.BoardPostDto;
import com.board.board.repository.BoardRepository;
import com.board.board.service.BoardApiService;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    private final BoardApiService boardApiService;

    public BoardApiController(BoardApiService boardApiService) {
        this.boardApiService = boardApiService;
    }

    @GetMapping("/boards")
    List<BoardPostDto> all(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String content ) {
//        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
//            return repository.findAll();
//        }else{
//            return repository.findByTitleOrContent(title, content);
//        }
        return boardApiService.findBoardApi(title, content);
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }


    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

//    @PreAuthorize("(isAuthenticated() and ( #userid == authentication.principal.userid )  ) or hasRole('ROLE_ADMIN')")
//    @PostAuthorize("returnObject.title == authentication.principal.username")
    @Secured("ROLE_ADMIN") // admin사용자만 delete 메소드를 호출할 수 있도록 한다.
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }

}