// 
// Decompiled by Procyon v0.5.36
// 
package OriginalNode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DownloadFile extends Thread {

    String host;
    int port;
    String filename;

    public DownloadFile(final String host, final int port, final String filename) {
        this.host = host;
        this.port = port;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            final Socket socket = new Socket(this.host, this.port);
            final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("GET DATA");
            final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename))));
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            out.close();
            br.close();
            socket.close();
        } catch (IOException thrown) {
            Logger.getLogger(DownloadFile.class.getName()).log(Level.SEVERE, null, thrown);
        }
    }
}
