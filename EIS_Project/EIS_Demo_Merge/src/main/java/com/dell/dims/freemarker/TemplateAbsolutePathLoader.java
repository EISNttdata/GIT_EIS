package com.dell.dims.freemarker;

import freemarker.cache.TemplateLoader;

import java.io.*;

/**
 * Created by Pramod_Kumar_Tyagi on 7/25/2016.
 */
public class TemplateAbsolutePathLoader implements TemplateLoader {

    public Object findTemplateSource(String name) throws IOException {
        File source = new File(name);
        return source.isFile() ? source : null;
    }

    public long getLastModified(Object templateSource) {
        return ((File) templateSource).lastModified();
    }

    public Reader getReader(Object templateSource, String encoding)
            throws IOException {
        if (!(templateSource instanceof File)) {
            throw new IllegalArgumentException("templateSource is a: " + templateSource.getClass().getName());
        }
        return new InputStreamReader(new FileInputStream((File) templateSource), encoding);
    }

    public void closeTemplateSource(Object templateSource) throws IOException {
        // Do nothing.
    }

}