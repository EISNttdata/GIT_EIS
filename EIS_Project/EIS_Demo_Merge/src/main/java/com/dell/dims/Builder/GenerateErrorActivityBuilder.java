package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.GenerateErrorActivity;
import freemarker.template.TemplateException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class GenerateErrorActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws IOException, SAXException, ParserConfigurationException, TemplateException, IllegalAccessException, InstantiationException, ClassNotFoundException, TransformerException {

        GenerateErrorActivity generateErrorActivity= (GenerateErrorActivity) activity;

        setInputSchemaQname(generateErrorActivity);
        setOutputSchemaQname(generateErrorActivity);

    }
}
