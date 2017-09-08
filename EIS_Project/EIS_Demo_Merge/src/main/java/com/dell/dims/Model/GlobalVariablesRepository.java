package com.dell.dims.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/4/2017.
 */
public class GlobalVariablesRepository
{

  //  private  final String fileName="defaultVars.substvar";
  //  private  final String defaultDirectory=DimsServiceImpl.input_project_path +File.separator+"defaultVars";
    public  List<GlobalVariables> globalVariables=new ArrayList<>();
    public  HashMap<String, List<String>> dirFiles = new HashMap<String, List<String>>();



    public void listFilesForFolder(File folder)
            throws IOException {

        if(folder.isDirectory()) {

            ArrayList<String> fileNames = new ArrayList<String>();


            for (final File fileEntry : folder.listFiles()) {

                if (fileEntry.isDirectory()) {
                    //   System.out.println(fileEntry.toString());
                    listFilesForFolder(fileEntry);
                } else {
                    String fileName = (fileEntry.getPath()).toString();
                    //   System.out.println(fileName);
                    fileNames.add(fileEntry.getPath());
                }
            }
            dirFiles.put(folder.getName(), fileNames);
            System.out.println(dirFiles);
        }
    }

    public List<GlobalVariables> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(List<GlobalVariables> globalVariables) {
        this.globalVariables = globalVariables;
    }
}


