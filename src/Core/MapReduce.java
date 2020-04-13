/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author fairytype
 */
public interface MapReduce extends Serializable{
    public void executeMap(String blockin, String blockout);
    public void executeReduce(Collection<String> blocks, String finalresults);
}
