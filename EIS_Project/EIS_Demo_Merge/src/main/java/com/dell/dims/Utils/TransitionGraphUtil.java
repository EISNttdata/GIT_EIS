package com.dell.dims.Utils;

import com.dell.dims.gop.GopNode;
import com.dell.dims.gop.Graphicable;

/**
 * Created by Manoj_Mehta on 1/16/2017.
 */
public class TransitionGraphUtil implements Graphicable {

    private GopNode source;
    private GopNode destination;
    private String label;

    @Override
    public void graph() {
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |  " + this.getLabel());
        System.out.println("        |");
        System.out.println("        \u25bc");
    }

    public GopNode getSource() {
        return source;
    }

    public void setSource(GopNode source) {
        this.source = source;
    }

    public GopNode getDestination() {
        return destination;
    }

    public void setDestination(GopNode destination) {
        this.destination = destination;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String routeGraph() {

        StringBuilder sb = new StringBuilder();
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        |  " + this.getLabel());
        sb.append(System.getProperty("line.separator"));
        sb.append("        |");
        sb.append(System.getProperty("line.separator"));
        sb.append("        \u25bc");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
}
