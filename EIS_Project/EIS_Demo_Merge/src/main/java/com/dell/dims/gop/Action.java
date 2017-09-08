package com.dell.dims.gop;

/**
 * @author pramod
 */
public interface Action {
    String getName();

    void execute(Token token);
}
