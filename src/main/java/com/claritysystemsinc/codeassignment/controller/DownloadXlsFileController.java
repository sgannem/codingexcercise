package com.claritysystemsinc.codeassignment.controller;

import com.claritysystemsinc.codeassignment.exception.ServiceException;
import com.claritysystemsinc.codeassignment.service.UploadService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DownloadXlsFileController extends BaseController {

    final private UploadService uploadService;

    @GetMapping(path = "/downloadFile/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathParam("filename") String fileName) {
        log.info("#Got fileName:"+fileName);
        AtomicReference<Path> foundFile = new AtomicReference<>();
        Path path = Paths.get(System.getProperty("java.io.tmpdir"));
        try {
            Files.list(path).forEach(file -> {
                if(file.getFileName().toString().equalsIgnoreCase(fileName)) {
                    foundFile.set(file);
                    return;
                }
            });
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\""+fileName+"\"";
        Resource resource = null;
        if(foundFile.get()!=null) {
            try {
                resource = new UrlResource(foundFile.get().toUri());
            } catch (MalformedURLException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

}