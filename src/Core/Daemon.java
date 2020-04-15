/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author fairytype
 */
public interface Daemon extends Remote {

    public void call(MapReduce m, String blockin, String blockout, CallBack cb) throws RemoteException;
}
