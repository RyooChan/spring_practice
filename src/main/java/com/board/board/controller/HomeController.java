package com.board.board.controller;

import com.board.board.dto.oauth.OAuthAttributes;
import com.board.board.dto.oauth.SessionUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

}
