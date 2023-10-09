package com.yc.demo.d0901;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class QQServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动成功，等待链接：8888");
        final Socket socket = serverSocket.accept();//进入阻塞状态
        final SocketAddress address = socket.getLocalSocketAddress();
        System.out.println("address=" + address);
        final InetAddress address1 = socket.getInetAddress();
        System.out.println("intenetAdress=" + address1.getHostAddress());
        final InputStream in = socket.getInputStream();
        final OutputStream outputStream = socket.getOutputStream();
        Scanner s = new Scanner(System.in);
        String other = "客户端";

        Thread t1=new Thread(()->{
            boolean run = true;
            while (run){
                byte[] bytes=new byte[1024];
                try {
                    int count = in.read(bytes);
                    String msg = new String(bytes, 0, count);
                    System.out.println("服务端:" + msg);
                    msg = s.nextLine();
                    outputStream.write(msg.getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            boolean run = true;
            while (run) {
                System.out.printf("我说");
                String string = s.nextLine();
                try {
                    outputStream.write(string.getBytes());
                    byte[] bytes = new byte[1024];
                    int count = in.read(bytes);
                    String msg = new String(bytes, 0, count);
                    System.out.println("客户端" + msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        t2.start();
        t1.join();//进入阻塞状态,不然会挂掉
        t2.join();
        socket.close();

    }
}
