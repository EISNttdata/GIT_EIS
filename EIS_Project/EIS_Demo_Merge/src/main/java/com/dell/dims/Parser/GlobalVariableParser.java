package com.dell.dims.Parser;

import com.dell.dims.Model.GlobalVariable;
import com.dell.dims.Model.GlobalVariables;
import com.dell.dims.Model.GlobalVariablesRepository;
import com.dell.dims.Parser.Utils.FileUtility;
import im.nll.data.extractor.Extractors;
import im.nll.data.extractor.utils.XmlUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static im.nll.data.extractor.Extractors.xpath;

public class GlobalVariableParser
{
    public GlobalVariablesRepository parseVariable(String filePath) throws Exception {
        GlobalVariablesRepository globalVariablesRepository = new GlobalVariablesRepository();
        globalVariablesRepository.listFilesForFolder(new File(filePath));
        Set<Map.Entry<String, List<String>>> entrySet = globalVariablesRepository.dirFiles.entrySet();

        // Collection Iterator
        Iterator<Map.Entry<String, List<String>>> iterator = entrySet.iterator();

        while(iterator.hasNext()) {

            Map.Entry<String, List<String>> entry = iterator.next();

            for(String path : entry.getValue()) {
                // InputStream is = GlobalVariableParser.class.getClassLoader().getResourceAsStream("GlobalVariable.xsd");
                String content =FileUtility.readFile(path, Charset.defaultCharset());
                globalVariablesRepository.getGlobalVariables().add(this.parseVariableGlobal(entry.getKey(),content));
            }
        }

        return globalVariablesRepository;
    }


    public GlobalVariables parseVariableGlobal(String category, String allFileElement) throws Exception {

        // get GlobalVariables str
        GlobalVariables globalVariables = new GlobalVariables();
        globalVariables.setCategory(category);
        String fileString =XmlUtils.removeNamespace(allFileElement);
        System.out.println(fileString);
        String globalVariablesStr = Extractors.on(fileString)
                .extract(xpath("//repository/globalVariables"))
                .asString();
        if(globalVariablesStr==null)
            return null;

        //extract globalvariable
        List<String> listGlobalVariable = Extractors.on(fileString)
                .split(xpath("//repository/globalVariables/globalVariable"))
                .asStringList();
        if(listGlobalVariable.isEmpty())
            return null;


        GlobalVariable globalVariable=null;
        for(String globalVar : listGlobalVariable)
        {
            // check if globalVar contains & then replace with &amp;
            if(globalVar.contains("&"))
            {
                globalVar=globalVar.replace("&","&amp;");
            }

            globalVariable = new GlobalVariable();
            globalVariable.setCategory(category);
            String name = Extractors.on(globalVar)
                    .extract(xpath("//globalVariable/name/text()"))
                    .asString();
            globalVariable.setName(name);
            String value = Extractors.on(globalVar)
                    .extract(xpath("//globalVariable/value/text()"))
                    .asString();
            globalVariable.setValue(value);
            String type = Extractors.on(globalVar)
                    .extract(xpath("//globalVariable/type/text()"))
                    .asString();

            globalVariable.setType(GlobalVariableType.valueOf(type));
            //    $_globalVariables/ns1:FileAdapter/ns1:SleepTime
            globalVariable.setXpath(getXpath(category, name));
            globalVariables.getGlobalVariables().add(globalVariable);
        }
        return globalVariables;
    }

    String getXpath(String category,String name)
    {
        return  "$_globalVariables"+"/gbl:"+category+"/gbl:"+name;
    }
}


/*
* xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.tibco.com/xmlns/repo/types/2002\">\n" +
        "    <globalVariables>\n" +
        "        <globalVariable>\n" +
        "            <name>Name test 1</name>\n" +
        "            <value>value test 1</value>\n" +
        "            <type>String</type>\n" +
        "            <deploymentSettable>true</deploymentSettable>\n" +
        "            <serviceSettable>false</serviceSettable>\n" +
        "            <modTime>13304412311412</modTime>\n" +
        "        </globalVariable>\n" +
        "        <globalVariable>\n" +
        "            <name>Name test 2</name>\n" +
        "            <value>value test 2</value>\n" +
        "            <type>String</type>\n" +
        "            <deploymentSettable>true</deploymentSettable>\n" +
        "            <serviceSettable>false</serviceSettable>\n" +
        "            <modTime>13304412311411</modTime>\n" +
        "        </globalVariable>\n" +
        "    </globalVariables>\n" +
        "</repository>";
* */


