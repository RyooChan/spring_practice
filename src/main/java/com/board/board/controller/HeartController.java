package com.board.board.controller;

import com.board.board.mapper.Heart.HeartMapper;
import com.board.board.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final HeartMapper heartMapper;

}
