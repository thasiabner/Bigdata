// 
// Decompiled by Procyon v0.5.36
// 
package Daemon;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Core.Slave;
import java.net.ServerSocket;

public class DaemonNode {

    static final String[] hosts = {"localhost", "localhost"};
    static final int[] ports = {8081, 8082};

    public static void main(String[] args) {
        try {
            final int serverPortIndex = Integer.parseInt(args[0]);
            final ServerSocket serverSocket = new ServerSocket(ports[serverPortIndex]);
            System.out.println("Server is ready");
            new DaemonRMI("daemon" + ports[serverPortIndex]).start();
            while (true) {
                new Slave(serverSocket.accept(), ports[serverPortIndex]).start();
            }
        } catch (IOException thrown) {
            Logger.getLogger(DaemonNode.class.getName()).log(Level.SEVERE, null, thrown);
        }
    }
}
