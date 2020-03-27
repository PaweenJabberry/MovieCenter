package com.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MovieCenterController {

    @RequestMapping("/")
    public String index() {
        return "web/index";
    }
}
