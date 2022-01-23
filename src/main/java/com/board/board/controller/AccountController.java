package com.board.board.controller;

import com.board.board.domain.User2;
import com.board.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid User2 user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "registerSelf";
        }
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/withdraw")
    public String withdraw(){
        return "account/withdraw";
    }

}
