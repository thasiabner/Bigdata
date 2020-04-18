// 
// Decompiled by Procyon v0.5.36
// 
package Daemon;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaemonSocket extends Thread {

    int port;
    Socket clientSocket;

    public DaemonSocket(final Socket clientSocket, final int port) {
        this.clientSocket = clientSocket;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String method = in.readLine();

            if (method.startsWith("POST")) {
                System.out.println("Writing file Daemon/file" + this.port + ".txt");
                final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("Daemon/file" + this.port + ".txt"))));
                String line;
                while ((line = in.readLine()) != null) {
                    bw.write(line);
                    bw.newLine();
                }
                bw.close();
                in.close();
            } else {
                System.out.println("Sending file to original node");
                File downloadFile = new File("Daemon/file" + this.port + ".txt");
                final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(downloadFile)));
                final PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
                String line;
                while ((line = br.readLine()) != null) {
                    out.println(line);
                }
                out.close();
                br.close();
                System.out.println("Send file to origin node successfully");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException thrown) {
            Logger.getLogger(DaemonSocket.class.getName()).log(Level.SEVERE, null, thrown);
        }
    }
}
