/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OriginalNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fairytype
 */
public class Split extends Thread {

    String bigFileName;

    public Split(String bigFileName) {
        this.bigFileName = bigFileName;
    }

    public void run() {
        String[] bigFileContent = getBigFileContent();

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
