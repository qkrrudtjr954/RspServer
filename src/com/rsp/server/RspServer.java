package com.rsp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RspServer {
    public static void main(String[] args) {
        RspServer server = new RspServer();
        server.running();
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

        int port = 3000;
//        while(true){
            try{
                server = new ServerSocket(port);
                System.out.println("================ CONNECTING ... =================");
                socket = server.accept();
                System.out.println(String.format("============= CONNECTED FROM %s / %s ================", socket.getInetAddress(), getTime()));

                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                int result = br.read();
                System.out.println("from client" + result);
                result = 100;

                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os);
                bw = new BufferedWriter(osw);

                bw.write(String.valueOf(result)+"(from server)");
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

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//    }
    static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
        return sdf.format(new Date());
    }
}