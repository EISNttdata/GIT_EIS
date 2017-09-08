package com.dell.dims.Utils;

import com.dell.dims.Model.*;
import com.dell.dims.Parser.TibcoBWProcessLinqParser;
import im.nll.data.extractor.utils.XmlUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Manoj_Mehta on 7/17/2017.
 */
public class StarterProcessFinder
{
    HashMap<String, HashSet> processMap=new HashMap<String,HashSet>();
    HashMap<String, String> allProcessWithPath=new HashMap<String,String>();
    HashMap<String, String> starterProcessWithPath=new HashMap<String,String>();

    HashSet<String> subProcessSet=new HashSet<String>();

    String parentProcessName="";
    String parentProcessPath="";



    public HashMap<String, String> getStarterProcess(String input_project_path) throws Exception {
        File dir = new File(input_project_path);

        if(!dir.exists())
        {
          System.out.println("No such directory found ");
        }
        //File dir = new File(input_project_path);

        String[] extensions = new String[]{"process"};
        List<File> tibcoProcessFiles = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for(File file: tibcoProcessFiles)
        {

            //System.out.println("Name:"+file.getName()+" Path:"+file.getAbsolutePath() +":: "+file.getPath() + "  :canonical:"+file.getCanonicalPath());


           // System.out.println("Name:"+file.getName()+" Path:"+new File(file.getAbsoluteFile().toString()).exists());
            parentProcessPath=file.getAbsolutePath();
            parentProcessName=file.getName();

            String fileString = XmlUtils.removeNamespace(IOUtils.toString(
                    new FileInputStream(file), "UTF-8"));

            //processing start for each file
             processFiles(fileString);

            // add parent and subprocess to Map
            processMap.put(parentProcessName,subProcessSet);
            allProcessWithPath.put(parentProcessName,parentProcessPath);
        }

        //Search Main Process
        Set<String> setToSearchFrom = new HashSet();
        Set<String> setToSearch = new HashSet();
        for(String key : processMap.keySet())
        {
            //System.out.println("Parent : "+key +"\nSubProcess :"+processMap.get(key) +"\n\n");

            HashSet<String> values=processMap.get(key);
            for(String val : values)
            {
                setToSearchFrom.add(val);
            }
            setToSearch.add(key);
        }

        //Now search Process not in setToSearch
        ArrayList<String> starterProcess=new ArrayList<String>();
        for(String searchKey : setToSearch)
        {
            boolean isExist=false;
            for(String searchKeyIn : setToSearchFrom)
            {
                if(searchKeyIn.contains(searchKey))
                {
                    isExist=true;
                }
            }

            if(!isExist)
            {
                starterProcess.add(searchKey);
            }
        }

        //Get Path for Starter Process
        for(String processName : starterProcess)
        {
            starterProcessWithPath.put(processName,allProcessWithPath.get(processName));
        }

        return starterProcessWithPath;
    }

   public TibcoBWProcess processFiles(String file) throws Exception {
       //ProcessDefinition pd;

       TibcoBWProcessLinqParser processParser = new TibcoBWProcessLinqParser();
       TibcoBWProcessLinqParser.props = PropertiesUtil.getPropertyFile();
       TibcoBWProcess tibcoBWProcess = processParser.parse(file);

       //   pd = new ProcessDefinition(tibcoBWProcess.getProcessName());//Process Name

       ArrayList<Activity> activities = (ArrayList<Activity>) tibcoBWProcess.getActivities();

       for (Activity activity : activities)
       {
           if (activity instanceof CallProcessActivity)
           {
               getSubProcess(activity);
           }
           else if (activity instanceof GroupActivity)
           {
               GroupActivity grpActivity = (GroupActivity) activity;
               List<Activity> activitiesList = grpActivity.getActivities();
               for (Activity innerActivity : activitiesList)
               {
                   if (innerActivity instanceof CallProcessActivity)
                   {
                       getSubProcess(innerActivity);
                   }
                   else if (innerActivity instanceof GroupActivity)
                   {
                       handleGroupActivity(innerActivity);
                   }
               }
           }

       }
       return tibcoBWProcess;
   }
    /**
     * @param activity
     */
    public String getSubProcess(Activity activity) throws Exception
    {
        String subProcess=null;
        CallProcessActivity callActivity= (CallProcessActivity) activity;
        List<ClassParameter> configs = callActivity.getConfigAttributes(callActivity);
        //get SUBprocessNAME FROM CONFIG
        for(ClassParameter param : configs)
        {
            if(param.getName().equalsIgnoreCase("processName"))
            {
               subProcess=param.getDefaultValue();
            }
        }
        //System.out.println("subprocessName :"+subProcess);

        if(subProcess!=null)
        {
            subProcessSet.add(subProcess);
        }
        return subProcess;
    }

    /*
     */
    public void handleGroupActivity(Activity innerActivity) throws Exception {
        GroupActivity grpActivityInner = (GroupActivity) innerActivity;
        List<Activity> activitiesListInner = grpActivityInner.getActivities();
        for (Activity actInnerOne : activitiesListInner) {
            if (actInnerOne instanceof CallProcessActivity)
            {
                getSubProcess(actInnerOne);
            }
            else if(actInnerOne instanceof GroupActivity)
            {
                handleGroupActivity(actInnerOne);
            }
        }
    }
}
