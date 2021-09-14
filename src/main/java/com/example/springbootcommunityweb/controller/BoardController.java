package com.example.springbootcommunityweb.controller;

import com.example.springbootcommunityweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping({"", "/"})
    public String board(@RequestParam(value ="idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "board/form";
    }

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model) { // pageableDefault 어노테이션의 파라미터인 size, sort 등을 이용해서 페이징 처리에 대한 규약을 정의한다.
        model.addAttribute("boardList", boardService.findBoardList(pageable));
        return "board/list";
    }
}
