package com.rsp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RspServer {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        RspServer server = new RspServer();
        server.running();
    }

    public RspServer() {
        int port = 3000;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void running(){
        ServerSocket server = null;
        Socket socket = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        while(true){
            try{
                System.out.println("================ CONNECTING ... =================");
                socket = this.serverSocket.accept();
                System.out.println(String.format("============= CONNECTED FROM %s / %s ================", socket.getInetAddress(), getTime()));

                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                int clientResult = br.read();
                String finalResult = rspLogic(clientResult);

                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os);
                bw = new BufferedWriter(osw);

                bw.write(finalResult+"(Response from server)");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    bw.close();
                    osw.close();
                    os.close();

                    br.close();
                    isr.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
        return sdf.format(new Date());
    }
    private String rspLogic(int clientResult){
        int serverResult = (int)(Math.random()*3) + 1;
        String finalResult = "";

        System.out.println(String.format("Client: %s vs Server: %s", clientResult, serverResult));
        if(clientResult<1 || clientResult>3){
            finalResult = "Wrong Input! (rock:1 cissors:2 paper:3)";
        }else{
            int result = clientResult - serverResult;
            if(result == -2 || result == 1){
                finalResult = "The winner is Client!";
            }else if (result == -1 || result == 2){
                finalResult = "The winner is Server!";
            }else{
                finalResult = "Draw .. try again!";
            }
        }
        System.out.println(finalResult);
        return finalResult;
    }
}
