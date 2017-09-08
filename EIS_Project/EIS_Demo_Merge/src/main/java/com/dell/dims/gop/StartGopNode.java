package com.dell.dims.gop;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author pramod
 */
public class StartGopNode extends GopNode {


    StartGopNode() {

    }

    public StartGopNode(String name) {
        super(name);
    }

    @Override
    public void graph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }
        System.out.println("+------------.");
        System.out.println("|<START NODE>|");
        System.out.println("+---" + padding + "---.");
        System.out.println("|   " + this.getName() + "   |");
        System.out.println("+---" + padding + "---+");

        Iterator<Transition> transitionIt = getTransitions().values().iterator();
        while (transitionIt.hasNext()) {
            transitionIt.next().graph();
        }
    }

    public String routeGraph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("+------------.");
        sb.append(System.getProperty("line.separator"));
        sb.append("|<START NODE>|");
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
    public void leave(Token token) {
        System.out.println("Starting process at " + new Date());
        //Watch out here.. we are duplicating code!!!
        System.out.println("Leaving " + this.getName());
        Collection<Transition> transitions = getLeavingTransitions().values();
        Iterator<Transition> it = transitions.iterator();
        if (it.hasNext()) {
            it.next().take(token);
        }
    }
//    There is no need to override this node, the start node never get executed
//    @Override
//    public void execute(Execution execution) {
//        super.execute(execution);
//    }


}
