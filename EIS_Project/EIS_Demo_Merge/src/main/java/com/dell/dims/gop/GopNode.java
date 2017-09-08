package com.dell.dims.gop;


import java.util.*;


/**
 * @author pramod
 */
public class GopNode implements Graphicable {

    private Long id;
    protected String name;

    private Map<String, Transition> leavingTransitions = new LinkedHashMap<String, Transition>();

    private Map<String, Action> actions = new HashMap<String, Action>();

    public GopNode(String name) {
        this.name = name;
    }

    public GopNode() {

    }

    public void enter(Token token) {
        token.setNode(this);
        System.out.println("Entering " + this.getName());
        execute(token);
    }

    public void execute(Token token) {
        System.out.println("Executing " + this.getName());
        if (actions.size() > 0) {
            Collection<Action> actionsToExecute = actions.values();
            Iterator<Action> it = actionsToExecute.iterator();
            while (it.hasNext()) {
                it.next().execute(token);
            }
            leave(token);
        } else {
            leave(token);
        }
    }

    public void leave(Token token) {
        System.out.println("Leaving " + this.getName());
        Collection<Transition> transitions = getLeavingTransitions().values();
        Iterator<Transition> it = transitions.iterator();
        if (it.hasNext()) {
            it.next().take(token);
        }
    }

    public void leave(String transition, Token mainToken) {
        System.out.println("Leaving " + this.getName());
        getLeavingTransitions().get(transition).take(mainToken);
    }


    public void addAction(Action action) {
        getActions().put(action.getName(), action);
    }

    public void addTransition(String event, GopNode destination) {
        getLeavingTransitions().put(event, new Transition(event, this, destination));
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) throws Exception {
        this.name = name;
    }

    /**
     * @return the leavingTransitions
     */
    public Map<String, Transition> getTransitions() {
        return getLeavingTransitions();
    }

    /**
     * the leavingTransitions to set
     */
    public void setTransitions(Map<String, Transition> transitions) {
        this.setLeavingTransitions(transitions);
    }

    public void graph() {
        String padding = "";
        String token = "-";
        for (int i = 0; i < this.getName().length(); i++) {
            padding += token;
        }


        System.out.println("+---" + padding + "---+");
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

    /**
     * @return the leavingTransitions
     */
    public Map<String, Transition> getLeavingTransitions() {
        return leavingTransitions;
    }

    /**
     * @param leavingTransitions the leavingTransitions to set
     */
    public void setLeavingTransitions(Map<String, Transition> leavingTransitions) {
        this.leavingTransitions = leavingTransitions;
    }

    /**
     * @return the actions
     */
    public Map<String, Action> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(Map<String, Action> actions) {
        this.actions = actions;
    }


    // default leaving transition and leaving transition ordering ///////////////

    /**
     * is the default leaving transition.
     */
    public Transition getDefaultLeavingTransition() {
        Transition defaultTransition = null;
        if ((leavingTransitions != null)
                && (leavingTransitions.size() > 0)) {
            defaultTransition = leavingTransitions.get(0);
        }
        return defaultTransition;
    }

    /**
     * retrieves a leaving transition by name. note that also the leaving
     * transitions of the supernode are taken into account.
     */
    public Transition getLeavingTransition(String transitionName) {
        Transition transition = null;
        if (leavingTransitions != null) {
            transition = getLeavingTransitions().get(transitionName);
        }

        return transition;
    }
}
