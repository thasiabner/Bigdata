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
public class DaemonImpl extends UnicastRemoteObject implements Daemon {

    public DaemonImpl() throws RemoteException {
    }

    @Override
    public void call(MapReduce m, String blockin, String blockout, CallBack cb) throws RemoteException {
        m.executeMap(blockin, blockout);
        cb.completed();
    }
}
