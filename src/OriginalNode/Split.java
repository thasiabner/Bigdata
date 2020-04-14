/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OriginalNode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fairytype
 */
public class Split extends Thread {

    String bigFileName;
    static String hosts[] = {"localhost", "localhost"};
    static int ports[] = {8081, 8082};
    static int nbHosts = hosts.length;
    static int nbChar;
//    static int nbActiveHosts;
//    static String activeHosts[];
//    static int activePorts[];

    public Split(String bigFileName) {
        this.bigFileName = bigFileName;
    }

    public void run() {
        String[] bigFileContent = getBigFileContent();
        if (bigFileContent.length == 0)
        	return;
        
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
        
        int nbCharEachBlock = (int)Math.ceil((double)nbChar / nbHosts);
        int start = 0;
    	try {
            for (int i = 0; i < nbHosts; ++i) {
            	Socket socket = new Socket(hosts[i], ports[i]);
            	DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            	ObjectOutputStream oos = new ObjectOutputStream(dout);
                Vector<String> block = new Vector<String>();
                int nbRealChar = 0;
                for (int j = start; j < bigFileContent.length; j++) {
					if (nbRealChar >= nbCharEachBlock)
					{
						block.add(0, "POST\n");
						oos.writeObject(block);
						oos.flush();
						oos.close();
						dout.close();
						socket.close();
						start = j;
						break;
					}
					else
					{
						block.add(bigFileContent[j]);
						nbRealChar += bigFileContent[j].length();
					}
				}
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    private String[] getBigFileContent() {
        Vector<String> block = new Vector();
        BufferedReader br = null;
        try {
            File bigFile = new File(bigFileName);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(bigFile)));
            String line;
            while ((line = br.readLine()) != null) {
                block.add(line);
                nbChar += line.length();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Split.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Split.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Split.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] bigFileContent = new String[block.size()];
        for (int i = 0; i < block.size(); i++) {
            bigFileContent[i] = block.get(i);
        }
        return bigFileContent;
    }
}
