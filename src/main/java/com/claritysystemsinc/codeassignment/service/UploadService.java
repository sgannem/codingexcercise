package com.claritysystemsinc.codeassignment.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public boolean upload(MultipartFile file);
}
