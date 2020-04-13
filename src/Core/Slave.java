/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Daemon.DaemonRMI;
import Daemon.DaemonSocket;
import java.net.Socket;

/**
 *
 * @author fairytype
 */
public class Slave extends Thread {
    
    Socket sock;
    int port;
    
    public Slave(Socket sock, int port) {
        this.sock = sock;
        this.port = port;
    }
    
    public void run() {
        try {
            String path = "daemon" + port;
            new DaemonRMI(path).start();
            new DaemonSocket(sock, port).start();
        } catch (Exception e) {
        }
    }
}
