/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author fairytype
 */
public class WordCount implements MapReduce {

    public static final String SEPARATOR = " - ";

    public void executeMap(String blockin, String blockout) {
        // read from blockin, compute count table, write to blockout		
        HashMap<String, Integer> hm = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(blockin)));
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(blockout)));
            String line;
            while ((line = br.readLine()) != null) {
                
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    if (hm.containsKey(tok)) {
                        hm.put(tok, hm.get(tok) + 1);
                    } else {
                        hm.put(tok, 1);
                    }
                }
            }
            for (String k : hm.keySet()) {
                bw.write(k + SEPARATOR + hm.get(k).toString());
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeReduce(Collection<String> blocks, String finalresults) {
        // read all files in blocks, merge and write to finalresults
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        try {
            for (String block : blocks) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(block)));
                String line;
                while ((line = br.readLine()) != null) {
                    String kv[] = line.split(SEPARATOR);
                    String k = kv[0];
                    int v = Integer.parseInt(kv[1]);
                    if (hm.containsKey(k)) {
                        hm.put(k, hm.get(k) + v);
                    } else {
                        hm.put(k, v);
                    }
                }
                br.close();
            }
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(finalresults)));
            for (String k : hm.keySet()) {
                bw.write(k + SEPARATOR + hm.get(k).toString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
