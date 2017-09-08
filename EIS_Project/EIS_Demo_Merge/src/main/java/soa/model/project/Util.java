package soa.model.project;

import java.io.File;


public class Util
{
  static boolean deleteDirectory(File dir)
  {
    boolean result = false;
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      if (f.isDirectory()) {
        result = deleteDirectory(f);
      } else {
        result = f.delete();
      }
    }
    result = dir.delete();
    return result;
  }
}
