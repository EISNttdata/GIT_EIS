package com.dell.dims.gop;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pramod
 */
public class ActivityGopNode extends GopNode {

    public ActivityGopNode(String name) {
        super(name);
    }

    public ActivityGopNode() {

    }

    @Override
    public void graph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }
        System.out.println("+---------------.");
        System.out.println("|<ACTIVITY NODE>|");
        System.out.println("+---" + padding + "---.");
        System.out.println("|   " + this.getName() + "   |");
        System.out.println("+---" + padding + "---+");

        Iterator<Transition> transitionIt = getTransitions().values().iterator();
        while (transitionIt.hasNext()) {
            transitionIt.next().graph();
        }
    }

    @Override
    public String routeGraph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("+---------------.");
        sb.append(System.getProperty("line.separator"));
        sb.append("|<ACTIVITY NODE>|");
        sb.append(System.getProperty("line.separator"));
        sb.append("+---" + padding + "---+");
        sb.append(System.getProperty("line.separator"));
        sb.append("|   " + this.getName() + "   |");
        sb.append(System.getProperty("line.separator"));
        sb.append("+---" + padding + "---+");
        sb.append(System.getProperty("line.separator"));


        Iterator<Transition> transitionIt = getTransitions().values().iterator();
        while (transitionIt.hasNext()) {
            sb.append(transitionIt.next().routeGraph());
        }
        return sb.toString();
    }

    @Override
    public void execute(Token token) {
        System.out.println("Executing the tibcoActivity... this could take a while...");
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ActivityGopNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("TibcoActivity Finished");
        leave(token);
    }

}
