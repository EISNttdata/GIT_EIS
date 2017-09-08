package com.dell.dims.Parser;


import com.dell.dims.Model.Activity;
import org.w3c.dom.Node;

public interface IActivityParser
{
    Activity parse(Node node, boolean isGroupActivity) throws Exception ;
}


