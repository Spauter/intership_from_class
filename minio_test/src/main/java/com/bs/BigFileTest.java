package com.bs;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 断点续船测试
 *
 * @author Bloduc Spauter
 */
public class BigFileTest {

    public static final String TARGET_FILE_PATH = "D:/upload/breakpoint resume repository/";

    public static String fileName;

    /**
     * 分块测试
     */
    @Test
    public void testChunk() throws IOException {
        JOptionPane.showMessageDialog(null, "请随意选择文件", "提示", JOptionPane.PLAIN_MESSAGE);
        JFileChooser open = new JFileChooser(new File("input.dat"));
        open.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        open.showOpenDialog(null);
        open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (open.getSelectedFile() == null) {
            JOptionPane.showMessageDialog(null, "没有选择文件", "没有文件", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File file = open.getSelectedFile();
        fileName = file.getName().split("\\.")[0];
        //分块大小
        int chunk = 1024 * 1024;
        int chunkNumber = (int) Math.ceil(file.length() * 1.0 / chunk);
        //使用刘从源文件读取数据，向分块中写入文件
        RandomAccessFile randomAccessFile = getRandomAccessFile(file, chunkNumber, chunk);
        randomAccessFile.close();
    }

    private @NotNull RandomAccessFile getRandomAccessFile(File file, int chunkNumber, int chunk) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        //缓冲区
        byte[] bytes = new byte[1024];
        String storePath = TARGET_FILE_PATH + fileName;
        File storeFile=new File(storePath);
        //创建分块文件存储的文件夹
        if (!storeFile.exists()) {
           boolean result= storeFile.mkdir();
            System.out.println(result);
        }
        for (int i = 0; i < chunkNumber; i++) {
            File chunkFile = new File(storePath+"/" + i);
            //分块文件写入流
            RandomAccessFile partFile = new RandomAccessFile(chunkFile, "rw");
            int len = -1;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                partFile.write(bytes, 0, len);
                if (partFile.length() >= chunk) {
                    break;
                }
            }
            partFile.close();
        }
        return randomAccessFile;
    }
}
