package com.dell.dims.Parser.Utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Created by Pramod_Kumar_Tyagi on 6/4/2017.
 */
public class FileUtility {


    /**
     * List all the files and folders from a directory
     * @param directoryName to be listed
     */
    public static void listFilesAndFolders(String directoryName){
      File directory = new File(directoryName);
      //get all the files from a directory
      File[] fList = directory.listFiles();
      for (File file : fList){
        System.out.println(file.getName());
      }
    }
    /**
     * List all the files under a directory
     * @param directoryName to be listed
     */
  public static void listFiles(String directoryName){
    File directory = new File(directoryName);
    //get all the files from a directory
    File[] fList = directory.listFiles();
    for (File file : fList){
      if (file.isFile()){
        System.out.println(file.getName());
      }
    }
  }
  /**
   * List all the folder under a directory
   * @param directoryName to be listed
   */
  public static void listFolders(String directoryName){
    File directory = new File(directoryName);
    //get all the files from a directory
    File[] fList = directory.listFiles();
    for (File file : fList){
      if (file.isDirectory()){
        System.out.println(file.getName());
      }
    }
  }
  /**
   * List all files from a directory and its subdirectories
   * @param directoryName to be listed
   */
  public static void listFilesAndFilesSubDirectories(String directoryName){
    File directory = new File(directoryName);
    //get all the files from a directory
    File[] fList = directory.listFiles();
    for (File file : fList){
      if (file.isFile()){
        System.out.println(file.getAbsolutePath());
      } else if (file.isDirectory()){
        listFilesAndFilesSubDirectories(file.getAbsolutePath());
      }
    }
  }

  public static String readFile(String path, Charset encoding)
    throws IOException
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  public static boolean createDirectories(String path)

  {
    File dir = new File(path);

    // create multiple directories at one time
    boolean successful = dir.mkdirs();
    if (successful) {
      // created the directories successfully
      System.out.println("directories were created successfully");
    }else
    {
      // something failed trying to create the directories
      System.out.println("failed trying to create the directories");
    }
    return successful;
  }

    public static void writeFile(String path,String fileName,String value,  OpenOption... options)
    {

        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File file = new File(path+ File.separator + fileName);


        try{
            //FileWriter fw = new FileWriter(file.getAbsoluteFile());
            // BufferedWriter bw = new BufferedWriter(fw);

            //  OutputStream outputStream = new BufferedOutputStream(
            // Files.newOutputStream(filePath, StandardOpenOption.CREATE,StandardOpenOption.APPEND));
            BufferedWriter bw= Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8,options);
            //Files.newBufferedWriter(file.getPath(),OpenOption)
            bw.write(value);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

    }

    }
