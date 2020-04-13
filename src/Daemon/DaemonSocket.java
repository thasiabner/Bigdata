/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daemon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author fairytype
 */
public class DaemonSocket extends Thread {

    int port;
    Socket server;

    public DaemonSocket(Socket server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {

        //Client connect
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String method = in.readLine();

            // IF: Daemon UPLOAD - ELSE: Daemon DOWNLOAD
            if (method.startsWith("POST")) {
                System.out.println("—Writing file daemon / file" + this.port + ".txt");
                File fileout = new File(".daemon / file" + this.port + ".txt");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout)));
                String line;
                while ((line = in.readLine()) != null) {
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
            } else {
                System.out.println("sending file to original node");
                File file = new File("daemon / map" + port + ".txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                OutputStream serverStream = server.getOutputStream();
                String line;
                while ((line = br.readLine()) != null) {
                    line += "\n";
                    serverStream.write(line.getBytes());
                }
                br.close();
                server.close();
                System.out.println("send file to origin node successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
