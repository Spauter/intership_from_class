package com.bs;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * 断点续船测试
 *
 * @author Bloduc Spauter
 */
public class BigFileTest {

    public static final String TARGET_FILE_PATH = "D:/upload/breakpoint resume repository/";

    public static String fileName;

    public static String store_file_path;

    /**
     * 分块测试
     */
    public  void division() throws IOException {
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
        File storeFile = new File(storePath);
        store_file_path = storePath;
        //创建分块文件存储的文件夹
        if (!storeFile.exists()) {
            boolean result = storeFile.mkdir();
            System.out.println(result);
        }
        for (int i = 0; i < chunkNumber; i++) {
            File chunkFile = new File(storePath + "/" + i);
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


    public void merge() throws IOException {
        JFileChooser save = new JFileChooser();
        save.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
        save.showSaveDialog(null);
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File savePath=save.getSelectedFile();
        File divisionFile = new File(store_file_path);
        //拿到所有文件
        File[] files = divisionFile.listFiles();
        //将数组转发List
        if (files == null) {
            System.out.println("No data");
            return;
        }
        List<File> list = Arrays.asList(files);
        list.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
        //遍历
        RandomAccessFile divisionFiles = new RandomAccessFile(savePath, "rw");
        //缓冲区
        byte[] bytes = new byte[1024];
        for (File file : list) {
            //读分块的流
            RandomAccessFile read = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = read.read(bytes)) != -1) {
                divisionFiles.write(bytes,0,len);
            }
            read.close();
        }
        divisionFiles.close();
    }

    public static void main(String[] args) throws IOException {
        BigFileTest bigFileTest=new BigFileTest();
        bigFileTest.division();
        bigFileTest.merge();
    }
}
