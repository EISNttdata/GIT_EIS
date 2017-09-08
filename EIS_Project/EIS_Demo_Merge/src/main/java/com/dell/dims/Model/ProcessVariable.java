
package com.dell.dims.Model;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class ProcessVariable {
    private ClassParameter parameter;

    public ClassParameter getParameter() {
        return parameter;
    }

    public void setParameter(ClassParameter parameter) {
        this.parameter = parameter;
    }

    private List<Node> ObjectXNodes = new ArrayList<Node>();

    public List<Node> getObjectXNodes() {
        return ObjectXNodes;
    }

    public void setObjectXNodes(List<Node> objectXNodes) {
        ObjectXNodes = objectXNodes;
    }
}


