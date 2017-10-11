package com.rsp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket socket = null;
        try{
            serverSocket = new ServerSocket(5000);
            System.out.println(getTime() + " server is running... ");
        } catch (IOException ex){
            ex.printStackTrace();
        }

        while(true){
            try{
                System.out.println(getTime() + "Wait Request...");

                //서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 기다린다.
                //클라인언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
                socket = serverSocket.accept();
                System.out.println(getTime() + "connect from "+ socket.getInetAddress() + " ...");

                InputStream in = socket.getInputStream();
                DataInputStream dis = new DataInputStream(in);

                while(true){
                    int data = dis.read();
                    if (data == -1){
                        break;
                    }
                    System.out.println(data);

                }
                dis.close();
                socket.close();
            } catch(IOException ex){
                ex.printStackTrace();
            }

            try{
                socket = serverSocket.accept();
                System.out.println(getTime() + "connect2 from "+ socket.getInetAddress() + " ...");

                //소켓의 출력 스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                dos.writeUTF("Text from server");
                System.out.println(getTime() + " message sent.");

                dos.close();
                socket.close();
            } catch(IOException ex){
                ex.printStackTrace();
            }


        }
    }
    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("[ hh : mm : ss ]");
        return sdf.format(new Date());
    }
}
