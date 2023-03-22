package com.claritysystemsinc.codeassignment.controller;

import com.claritysystemsinc.codeassignment.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController extends BaseController {

    @GetMapping(path = "/ping")
    public ResponseEntity<ResponseDto> ping() {
        log.info("#ping is called");
        return getSuccessResponseEntityWithDataAndMessage(null,"pong");
    }

}
