package com.bs;

import io.minio.*;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.*;


/**
 * @description 测试MinIO
 * @author Mr.M
 * @date 2022/9/11 21:24
 * @version 1.0
 */
public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.24.129:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

   //上传文件
    @Test
    public  void upload() {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("mediafiles")
                    //添加子目录
                    .object("001/test001.bmp")
                    .filename("E:\\backup\\spauter\\Genshin Impact\\胡桃 (7).bmp")
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }
    }
    @Test
    public void getFile() throws IOException {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("mediafiles").object("001/test001.bmp").build();
        try(
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream("C:\\users\\32306\\desktop\\1.bmp");
        ) {
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream1 = new FileInputStream("E:\\backup\\spauter\\Genshin Impact\\胡桃 (7).bmp");
        String source_md5 = DigestUtils.md5Hex(fileInputStream1);
        FileInputStream fileInputStream = new FileInputStream("C:\\users\\32306\\desktop\\1.bmp");
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if(source_md5.equals(local_md5)){
            System.out.println("Download success");
        }else {
            System.out.println("Download failed");
        }

    }
}
