/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OriginalNode;

/**
 *
 * @author fairytype
 */
public class OriginalNode {

    public static void main(String args[]) {

        String bigFileName;
        if(args.length != 2) {

            System.out.println(
            "java OriginNode <split / launch > <BigFile Name>");
		return;
        }
        bigFileName = args[1];
        // Start split files
        Split split = new Split(bigFileName);
        split.run();
        //Finish split file

        //Start launch the process
        Launch launch = new Launch();
        launch.run();

        //Finish launch
        System.exit(0);

    }
}
