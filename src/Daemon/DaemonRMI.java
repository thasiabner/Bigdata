/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daemon;

import Core.DaemonImpl;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author fairytype
 */
public class DaemonRMI extends Thread{

    String path;

    public DaemonRMI(String path) {
        this.path = path;
    }

    public void run() {
        try {
            Registry registry = LocateRegistry.createRegistry(5000);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            Naming.rebind("//localhost:5000/" + this.path, new DaemonImpl());
            System.out.println("RMI is ready");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
