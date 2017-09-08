package com.dell.dims.gop;

import java.util.Date;
import java.util.Iterator;

/**
 * @author pramod
 */
public class EndGopNode extends GopNode {

    EndGopNode() {

    }

    public EndGopNode(String name) {
        super(name);
    }


    @Override
    public void graph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }
        System.out.println("+----------.");
        System.out.println("|<END NODE>|");
        System.out.println("+---" + padding + "---.");
        System.out.println("|   " + this.getName() + "   |");
        System.out.println("+---" + padding + "---+");

        Iterator<Transition> transitionIt = getTransitions().values().iterator();
        while (transitionIt.hasNext()) {
            transitionIt.next().graph();
        }
    }



    @Override
    public void execute(Token token) {
        System.out.println("Ending process at " + new Date());
    }

    @Override
    public void addTransition(String event, GopNode destination) {
        throw new UnsupportedOperationException("You could not add leaving transtions to an End GopNode ! ");
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
        sb.append("|<END NODE>|");
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
}
