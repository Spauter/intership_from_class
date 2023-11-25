package com.bs.biz;

import org.springframework.web.multipart.MultipartFile;

public interface FastDFSBiz {
    String upload(MultipartFile multipartFile);
}
