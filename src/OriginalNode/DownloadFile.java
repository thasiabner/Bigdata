/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OriginalNode;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fairytype
 */
public class DownloadFile extends Thread {

    String host;
    int port;
    String filename;

     public DownloadFile(String host, int port, String filename) {
        this.host = host;
        this.port = port;
        this.filename = filename;
    }

    public void run() {

        try {
            Socket s = new Socket(host, port);
            InputStream is = s.getInputStream();
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            
            oos.writeChars("GET DATA");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            String line = "";
            int charNum;
            while ((charNum = is.read()) != -1) {
                line += (char) charNum;
            }
            bw.write(line);
        } catch (IOException ex) {
            Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
