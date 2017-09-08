package com.dell.dims.gop;

/**
 * @author pramod
 */
public interface NodeContainer {
    void addNode(GopNode node);

    GopNode getNode(Long id);
}
