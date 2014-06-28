package org.shigglewitz.chess.controller;

import org.shigglewitz.chess.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
}
