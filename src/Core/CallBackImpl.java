/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author fairytype
 */
public class CallBackImpl extends UnicastRemoteObject implements CallBack {

    int nbnode;
    int completedNode = 0;

    public CallBackImpl(int n) throws RemoteException {
        nbnode = n;
    }

    public synchronized void completed() throws RemoteException {
        completedNode++;
        notify();
    }

    public synchronized void waitforall() {
        if (completedNode < nbnode) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
