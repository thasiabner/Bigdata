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

    public CallBackImpl(int n) throws RemoteException {
        nbnode = n;
    }

    public synchronized void completed() throws RemoteException {
        notify();
    }

    public synchronized void waitforall() {
        for (int i = 0; i < nbnode; i++) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
