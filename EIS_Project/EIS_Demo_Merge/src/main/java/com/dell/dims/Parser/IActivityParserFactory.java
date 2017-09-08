
package com.dell.dims.Parser;

public interface IActivityParserFactory
{
    IActivityParser getParser(String activityType) throws Exception ;

}


