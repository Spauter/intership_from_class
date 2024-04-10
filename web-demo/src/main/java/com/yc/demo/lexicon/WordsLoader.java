package com.yc.demo.lexicon;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordsLoader {
    public List<String> ReadLexicon(File file) {
        List<String> list = new ArrayList<>();
        FileReader iis = null;
        BufferedReader bfr = null;
        try {
            iis = new FileReader(file);
            bfr = new BufferedReader(iis);
            String info = null;
            while ((info = bfr.readLine()) != null) {
                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "错误/n" + e, "系统错误", JOptionPane.ERROR_MESSAGE);
        }
        if (null != iis) {
            try {
                iis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> ReadLexicon(String path) {
        File file = new File(path);
        return ReadLexicon(file);
    }
}
