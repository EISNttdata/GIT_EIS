package com.dell.dims.gop;

/**
 * @author pramod
 */
public class Transition implements Graphicable {
    private GopNode source;
    private GopNode destination;
    private String label;


    public Transition() {

    }

    public Transition(String label, GopNode source, GopNode destination) {
        this.source = source;
        this.destination = destination;
        this.label = label;
    }

    public void take(Token token) {
        System.out.println("Taking transition to " + getDestination().getName());
        this.getDestination().enter(token);

    }

    /**
     * @return the destination
     */
    public GopNode getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(GopNode destination) {
        this.destination = destination;
    }

    /**
     * @return the source
     */
    public GopNode getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(GopNode source) {
        this.source = source;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void graph() {
        System.out.println("        |");
        System.out.println("        |");
        System.out.println("        |  " + this.getLabel());
        System.out.println("        |");
        System.out.println("        \u25bc");

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
