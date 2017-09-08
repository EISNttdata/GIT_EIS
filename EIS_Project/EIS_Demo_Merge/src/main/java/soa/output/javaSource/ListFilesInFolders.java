package soa.output.javaSource;

/**
 * Created by Manoj_Mehta on 4/6/2017.
 */

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;
public class ListFilesInFolders{
    /****** START SET/GET METHOD, DO NOT MODIFY *****/
    protected String rootPath = "";
    protected String[] fileExtensions = null;
    protected String[] fileNames = null;
    public String getrootPath() {
        return rootPath;
    }
    public void setrootPath(String val) {
        rootPath = val;
    }
    public String[] getfileExtensions() {
        return fileExtensions;
    }
    public void setfileExtensions(String[] val) {
        fileExtensions = val;
    }
    public String[] getfileNames() {
        return fileNames;
    }
    public void setfileNames(String[] val) {
        fileNames = val;
    }
    /****** END SET/GET METHOD, DO NOT MODIFY *****/
    public ListFilesInFolders() {
    }
    public void invoke() throws Exception {
/* Available Variables: DO NOT MODIFY
	In  : String rootPath
	In  : String[] fileExtensions
	Out : String[] fileNames
* Available Variables: DO NOT MODIFY *****/
        Vector v = new Vector();
        int counter = 0;
        File root = new File(rootPath);
        listFilesRecursive(v, root,fileExtensions);
        Enumeration e = v.elements();
        String[] fileNames = new String[v.size()];
        while(e.hasMoreElements())
        {
            File fn;
            fn = (File)e.nextElement();
            // System.out.println(fn.getPath());
            fileNames[counter++] = fn.getPath();
        }
        setfileNames(fileNames);
    }


    public static void listFilesRecursive(Vector list, File rootPath, String[] fileExtensions) {
        if (rootPath.isFile()) {
            list.addElement(rootPath);
            return;
        }
        File[] files = rootPath.listFiles();
        for (int i=0; i < files.length; i++) {
            if ( files[i].isFile()){
                for(int f = 0; f < fileExtensions.length; f++) {
                    if ( files[i].getName().endsWith(fileExtensions[f]) ) {
                        list.addElement(files[i]);
                        //return;
                    }
                }
            }
            if ( files[i].isDirectory()) {
		/*Ignore archive and fromhphc folders */
                if(!(files[i].getAbsolutePath().endsWith("arch".toLowerCase()) ||
                        files[i].getAbsolutePath().endsWith("fromhphc".toLowerCase()) ||
                        files[i].getAbsolutePath().endsWith("archive".toLowerCase())
                )){

                    listFilesRecursive(list,files[i], fileExtensions);
                }
            }
        }
    }

}