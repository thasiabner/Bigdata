/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OriginalNode;

import Core.CallBackImpl;
import Core.MapReduce;
import Core.WordCount;
import Core.Daemon;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author fairytype
 */
public class Launch extends Thread {

    static String hosts[] = {"localhost", "localhost"};
    static int ports[] = {8081, 8082};
    static int nbHosts = hosts.length;
    static String fileResult = "originNode/finalResult.txt";

    public void run() {
        MapReduce wc = new WordCount();
        CallBackImpl cb;
        long t1 = 0;
        try {
            cb = new CallBackImpl(nbHosts);
            //Mapping files in all servers
            t1 = System.currentTimeMillis(); // To calculate the start time of running multiple daemon
            for (int i = 0; i < nbHosts; ++i) {
                Daemon daemon = (Daemon) Naming.lookup("//localhost:5000/daemon" + ports[i]);
                String blockin = "daemon/file" + ports[i] + ".txt";
                String blockout = "daemon/map" + ports[i] + ".txt";
                daemon.call((MapReduce) wc, blockin, blockout, cb);
            }
            cb.waitforall();
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

//Map file successfully
//Download all files
        DownloadFile[] file = new DownloadFile[nbHosts];
        Vector<String> blocks = new Vector<String>();
        for (int i = 0; i < nbHosts; ++i) {
            String filename = "originNode/map" + ports[i] + ".txt";
            blocks.add(filename);
            file[i] = new DownloadFile(hosts[i], ports[i], filename);
            file[i].start();
        }
        for (int i = 0; i < nbHosts; ++i) {
            try {
                file[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Download all file successfully
// Reduce fle successfully
        wc.executeReduce(blocks, fileResult);
        //Measure time when run many daemons => to compare with the case that run serialization
        long t2 = System.currentTimeMillis();

        System.out.println("Time: " + (t2 - t1));
    }
}
