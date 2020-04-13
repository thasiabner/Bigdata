/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daemon;

import Core.Slave;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fairytype
 */
public class DaemonNode {

    static String hosts[] = {"localhost", "localhost"};
    static int ports[] = {8081, 8082};

    public static void main(String[] args) {
        try {
            int i = Integer.parseInt(args[0]);
            ServerSocket ss = new ServerSocket(ports[i]);
            System.out.println("Server is ready");
            while (true) {
                new Slave(ss.accept(), ports[i]).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(DaemonNode.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
