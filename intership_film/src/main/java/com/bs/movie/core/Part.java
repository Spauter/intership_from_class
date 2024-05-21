package com.bs.movie.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Company 源辰信息
 * 文件处理类
 * @author navy
 * @date 2024/3/2
 * @Email haijunzhou@hnit.edu.cn
 */
public class Part {
    private String fileName; // 文件名
    private String name; // 表单名
    private byte[] data; // 文件数据
    private String type; // 文件的类型

    public Part() {
    }

    public Part(String name, String fileName, String type) {
        this.name = name;
        this.fileName = fileName;
        this.type = type;
    }

    public Part(String name, String fileName, String type, byte[] data) {
        this.name = name;
        this.fileName = fileName;
        this.type = type;
        this.data = data;
    }

    public void write(String path) {
        if (data != null && data.length > 0) {
            try (FileOutputStream fos = new FileOutputStream(new File(path))) {
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Part(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "File [fileName=" + fileName + ", name=" + name + ", type=" + type + "]";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(fileName, part.fileName) && Objects.equals(name, part.name) && Arrays.equals(data, part.data) && Objects.equals(type, part.type);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName, name, type);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
