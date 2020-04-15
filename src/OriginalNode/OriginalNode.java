// 
// Decompiled by Procyon v0.5.36
// 
package OriginalNode;

public class OriginalNode {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("java OriginNode <BigFile Name>");
            return;
        }
        new Split(args[0]).run();
        new Launch().run();
        System.exit(0);
    }
}
