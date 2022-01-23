package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.domain.User2;
import com.board.board.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class UserApiController {

    @Autowired
    private UserRepository2 repository;

    @GetMapping("/users")
    List<User2> all() {
        List<User2> users =  repository.findAll();
        users.get(0).getBoards().size();
        return users;
    }

    @PostMapping("/users")
    User2 newUser(@RequestBody User2 newUser) {
        return repository.save(newUser);
    }


    @GetMapping("/users/{id}")
    User2 one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User2 replaceUser(@RequestBody User2 newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setBoards(newUser.getBoards());
                    for(Board board : user.getBoards()){
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}