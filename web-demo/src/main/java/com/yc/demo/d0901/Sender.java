package com.yc.demo.d0901;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Sender {
    static Scanner scanner = new Scanner(System.in);

    public static void sendText(OutputStream outputStream, String msg) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeUTF(msg);
    }

    public static String recvText(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
       return dataInputStream.readUTF();
    }

    public static Object recv(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int i = dataInputStream.readInt();
        return i == 1 ? recvText(inputStream) : recvText(inputStream);

    }

    public static void send(OutputStream outputStream)throws IOException{
        System.out.println("请输入需要发送的文件类型:1代表文字,2代表文件");
        final String i=scanner.nextLine();
        if("1".equals(i)){
            System.out.println("请输入文件类容");
            String msg= scanner.nextLine();
            sendText(outputStream,msg);
        }else if("2".equals(i)){
            JFileChooser jFileChooser=new JFileChooser(System.getenv("TEMP"));
            jFileChooser.showOpenDialog(null);
            final File file=jFileChooser.getSelectedFile();


        }
    }

    public  static void sendFile(OutputStream outputStream,String path){

    }


    public static File recvFile(InputStream inputStream) throws IOException {
        String savePath = System.getenv("TEMP");//C:\\Users\\用户\\APPDATA\\Local\\Temp
        return null;
    }
}
