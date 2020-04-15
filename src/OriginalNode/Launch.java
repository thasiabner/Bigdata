// 
// Decompiled by Procyon v0.5.36
// 
package OriginalNode;

import java.util.Collection;
import java.util.Vector;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import Core.CallBack;
import Core.MapReduce;
import java.rmi.Naming;
import Core.Daemon;
import Core.CallBackImpl;
import Core.WordCount;

public class Launch extends Thread {

    static String[] hosts = {"localhost", "localhost"};
    static int[] ports = {8081, 8082};
    static int nbHosts = hosts.length;
    static String fileResult = "OriginalNode/finalResult.txt";

    @Override
    public void run() {
        final WordCount wc = new WordCount();
        CallBackImpl cb;
        long t1 = 0;
        try {
            cb = new CallBackImpl(nbHosts);
            //Mapping files in all servers
            t1 = System.currentTimeMillis(); // To calculate the start time of running multiple daemon
            for (int i = 0; i < nbHosts; ++i) {
                Daemon daemon = (Daemon) Naming.lookup("//localhost:5000/daemon" + ports[i]);
                String blockin = "Daemon/file" + ports[i] + ".txt";
                String blockout = "Daemon/map" + ports[i] + ".txt";
                daemon.call((MapReduce) wc, blockin, blockout, cb);
            }
            cb.waitforall();
            System.out.println("Done mapping");
        } catch (MalformedURLException | RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

        System.out.println("Downloading...");
        DownloadFile[] file = new DownloadFile[nbHosts];
        Vector<String> blocks = new Vector<String>();

        for (int i = 0; i < nbHosts; ++i) {
            String filename = "OriginalNode/map" + ports[i] + ".txt";
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
