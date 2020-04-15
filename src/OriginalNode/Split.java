// 
// Decompiled by Procyon v0.5.36
// 
package OriginalNode;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.Vector;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Split extends Thread {

    String bigFileName;
    static String[] hosts = {"localhost", "localhost"};
    static int[] ports = {8081, 8082};
    static int nbHosts = hosts.length;
    //    static int nbActiveHosts;
//    static String activeHosts[];
//    static int activePorts[];

    public Split(final String bigFileName) {
        this.bigFileName = bigFileName;
    }

    @Override
    public void run() {
        int nbChar = getTotalChar();
        if (nbChar == 0) {
            return;
        }

        //        try {
//        	nbActiveHosts = 0;
//			for (int i = 0; i < hosts.length; i++) {
//            	Socket socket = new Socket(hosts[i], ports[i]);
//            	activeHosts[nbActiveHosts] = hosts[nbActiveHosts];
//            	activePorts[nbActiveHosts] = ports[nbActiveHosts];
//            	nbActiveHosts++;
//            	socket.close();
//			}
//		} catch (Exception e) {
//			
//		}
        final int nbCharEachBlock = (int) Math.ceil(nbChar / (double) nbHosts);
        int start = 0;
        BufferedReader in = null;
        File bigFile = new File(bigFileName);
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(bigFile)));
            String line;
            for (int hostIndex = 0; hostIndex < nbHosts; hostIndex++) {
                final Socket socket = new Socket(hosts[hostIndex], ports[hostIndex]);
                final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("POST");
                int nbRealChar = 0;
                while ((line = in.readLine()) != null) {
                    out.println(line);
                    nbRealChar += line.length();
                    if (nbRealChar > nbCharEachBlock) {
                        out.close();
                        socket.close();
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Split.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Split.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            for (int i = 0; i < Split.nbHosts; ++i) {
//                final Socket socket = new Socket(hosts[i], ports[i]);
//                final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                out.println("POST");
//                int nbRealChar = 0;
//                for (int j = start; j < bigFileContent.length; ++j) {
//                    if (nbRealChar >= nbCharEachBlock) {
//                        out.close();
//                        socket.close();
//                        start = j;
//                        break;
//                    }
//                    out.println(bigFileContent[j]);
//                    nbRealChar += bigFileContent[j].length();
//                }
//            }
//        } catch (Exception ex) {
//        }
    }

    private int getTotalChar() {
        int nbChar = 0;
        BufferedReader in = null;
        try {
            File bigFile = new File(bigFileName);
            in = new BufferedReader(new InputStreamReader(new FileInputStream(bigFile)));
            String line;
            while ((line = in.readLine()) != null) {
                nbChar += line.length();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return nbChar;
    }

}
