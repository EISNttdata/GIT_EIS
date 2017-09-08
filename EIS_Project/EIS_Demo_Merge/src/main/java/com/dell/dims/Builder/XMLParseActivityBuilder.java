package com.dell.dims.Builder;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;
import com.dell.dims.Model.XmlParseActivity;
import com.dell.dims.service.DimsServiceImpl;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/18/2017.
 */
public class XMLParseActivityBuilder extends AbstractActivityBuilder {
    @Override
    public void build(Activity activity) throws Exception{


        //CheckFileInPath

        System.out.println("-------------Activity Name ::---------"+activity.getName());
        // TibcoBWProcess currentProcess;
        String projectName= DimsServiceImpl.projectName;

        //This should be main process name fetched from tibco
        // currentProcess= new TibcoBWProcess(projectName);
        XmlParseActivity xmlParseActivity= (XmlParseActivity) activity;

        setInputSchemaQname(xmlParseActivity);
        setOutputSchemaQname(xmlParseActivity);

       // Get config Properties
        List<ClassParameter> configProps=((XmlParseActivity) activity).getConfigAttributes(xmlParseActivity);

        //generate common properties
        doCreateGenericProperties(xmlParseActivity,true,configProps);
    }
}
