package com.bs.contriller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author BS
 */
@RestController
@RequestMapping("one")
@Slf4j
public class UploadController {
    public String uploadToWindows(MultipartFile multipartFile, boolean repeatable) {
        String fileName = multipartFile.getOriginalFilename();
        File file;
        try {
            if (!repeatable) {
                String suffix = null;
                if (fileName != null) {
                    suffix = fileName.replaceAll(".+(\\.\\w+)", "$1");
                }
                String prefix = UUID.randomUUID().toString();
//                fileName =PROJECT_WEB_PATH + destPath + "\\" + ;
                fileName = "D:/upload/" + prefix + suffix;
            } else {
                fileName = "D:/upload/" + fileName;
            }
            System.out.println(fileName);
            file = new File(fileName);
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
            return "/" + file.getName();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return fileName;
    }


    @RequestMapping("upload")
    public Map<String, Object> addMedia(@RequestParam("image") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String osName = System.getProperty("os.name");
        String fileName = file.getOriginalFilename();
        log.info("fileName"+fileName);
        try {
            if (osName.startsWith("Windows")) {
                String path=uploadToWindows(file, false);
                map.put("code",200);
                map.put("msg",path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", e.getMessage());

        }
        return map;
    }
}
