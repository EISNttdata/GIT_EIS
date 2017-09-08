package com.dell.dims.gop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author pramod
 */
public class Token {

    protected String name = null;
    private GopNode node;

    protected Token parent = null;

    protected Map children = null;

   // protected ProcessInstance processInstance = null;

    public GopNode getNode() {
        return node;
    }

    public void setNode(GopNode node) {
        this.node = node;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean hasParent() {
        return (parent != null);
    }

    public Token getParent() {
        return parent;
    }

    public void setParent(Token parent) {
        this.parent = parent;
    }

    void addChild(Token token) {
        if (children == null) {
            children = new HashMap();
        }
        children.put(token.getName(), token);
    }

    public Map getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Token() {

    }

    /**
     * creates a root token.
     */
  /*  public Token(ProcessInstance processInstance) {

        this.processInstance = processInstance;
        this.node = processInstance.getProcessDefinition().getStartState();
        // this.isTerminationImplicit = processInstance.getProcessDefinition().isTerminationImplicit();

        // optimization:  assigning an id is not necessary since the process instance will be saved shortly.
        // Services.assignId(this);
    }*/


    public void collectChildrenRecursively(List tokens) {
        if (children != null) {
            Iterator iter = children.values().iterator();
            while (iter.hasNext()) {
                Token child = (Token) iter.next();
                tokens.add(child);
                child.collectChildrenRecursively(tokens);
            }
        }
    }

}
