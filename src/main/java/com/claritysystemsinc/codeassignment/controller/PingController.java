package com.claritysystemsinc.codeassignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {

    @GetMapping(path = "/ping")
    public String ping() {
        log.info("#ping is called");
        return "pong";
    }

}
