package com.yc.demo.d0901;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class QQlinet {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("172.18.50.122",8888);
        final InputStream in=socket.getInputStream();
        final OutputStream outputStream=socket.getOutputStream();
        Scanner s=new Scanner(System.in);
        boolean run=true;
        while (run){
            byte[] bytes=new byte[1024];
            int count=in.read(bytes);
            String msg=new String(bytes,0,count);
            System.out.println("服务端:"+msg);
            msg=s.nextLine();
            outputStream.write(msg.getBytes());
        }
        socket.close();


    }
}
