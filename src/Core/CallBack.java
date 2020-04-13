/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author fairytype
 */
public interface CallBack extends Remote {

    void completed() throws RemoteException;
}
