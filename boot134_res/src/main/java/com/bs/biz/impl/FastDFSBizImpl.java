package com.bs.biz.impl;


import com.bs.biz.FastDFSBiz;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;

@Slf4j
@Component
public class FastDFSBizImpl implements FastDFSBiz {
    @Autowired
    private FastFileStorageClient storageClient;
    @Override
    public String upload(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            log.info("上传的文件名" + multipartFile.getOriginalFilename());
            //TODO  此处会抛出NullPointerException异常,需要解决
            log.info("获得的文件后缀名" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            StorePath storePath = storageClient.uploadFile(IOUtils.toByteArray(inputStream),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()));

            log.info("文件上传成功，路径信息:" + storePath);
            log.info("groupd:" + storePath.getGroup());
            log.info("path:" + storePath.getPath());
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return "group1/M00/00/00/rBIAA2VLeLuAXn8nABv8brMLpKg191.png";
        }

    }
}
