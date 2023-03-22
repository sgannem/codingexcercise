package com.claritysystemsinc.codeassignment.controller;

import com.claritysystemsinc.codeassignment.common.dto.ResponseDto;
import com.claritysystemsinc.codeassignment.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadXlsFileController extends BaseController {

    final private UploadService uploadService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> upload(@RequestParam("file") MultipartFile file) {
        boolean uploadStatus = uploadService.upload(file);
        if (uploadStatus) {
            return getSuccessResponseEntityWithDataAndMessage(null, "successfully uploaded file");
        } else {
            return getErrorResponseEntityWithDataAndMessage(null, "upload file is failed");
        }
    }

}