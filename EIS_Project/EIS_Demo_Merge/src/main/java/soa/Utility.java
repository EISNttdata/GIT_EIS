package soa;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import soa.model.project.OutputProject;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/*import oracle.migrationtool.parser.model.BPELDocument;
import oracle.migrationtool.parser.model.BPELDocument.PartnerLink;
import oracle.migrationtool.parser.model.project.OutputProject;*/


public class Utility
{
  public static URL createURL(String fileName)
    throws Exception
  {
    URL url = null;
    try {
      url = new URL(fileName);
    } catch (MalformedURLException ex) {
      File f = new File(fileName);
      try {
        String path = f.getAbsolutePath();



        String fs = System.getProperty("file.separator");
        if (fs.length() == 1) {
          char sep = fs.charAt(0);
          if (sep != '/') {
            path = path.replace(sep, '/');
          }
          if (path.charAt(0) != '/') {
            path = '/' + path;
          }
        }
        path = "file://" + path;
        url = new URL(path);
      } catch (MalformedURLException e) {
        System.out.println("Cannot create url for: " + fileName);
        throw new Exception("Cannot create url for: " + fileName);
      }
    }
    return url;
  }

  public static String serializeXML(Document doc) {
    String xmlString = null;
    try
    {
      TransformerFactory transfac = TransformerFactory.newInstance();

      Transformer trans = transfac.newTransformer();
      trans.setOutputProperty("omit-xml-declaration", "yes");
      trans.setOutputProperty("indent", "yes");


      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      DOMSource source = new DOMSource(doc);
      trans.transform(source, result);
      xmlString = sw.toString();

    }
    catch (Exception e)
    {

      e.printStackTrace();
    }
    return xmlString;
  }







  public static String getFilePathForXQuery(String path)
  {
    path = path.replace(" ", "%20");
    return "file:///" + path.replace('\\', '/');
  }

  public static String getFilePathForComposite(String path)
  {
    path = path.replace(" ", "%20");
    return path.replace('\\', '/');
  }

  public static String readFile(String filePath)
  {
    StringBuilder  value = new StringBuilder();
    BufferedReader reader = null;
    try {
      FileReader f = new FileReader(new File(filePath));
      reader = new BufferedReader(f);
      String line = null;
      while ((line = reader.readLine()) != null) {
        value.append(line);
        value.append("\n");
      }











      return value.toString();
    }
    catch (IOException ex)
    {
      Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ex) {
          Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return filePath;
  }


  public static String readFile(InputStreamReader inputStreamReader)
  {
    StringBuilder value = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(inputStreamReader);
      String line = null;
      while ((line = reader.readLine()) != null) {
        value.append(line);
        value.append("\n");
      }











      return value.toString();
    }
    catch (IOException ex)
    {
      Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException ex) {
          Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return null;
  }

  public static void writeOutput(String filePath, String value)
  {
    FileWriter writer = null;
    try {
      File output = new File(filePath);

      File fParent = new File(output.getParent());
      fParent.mkdirs();
      if ((!output.exists()) &&
        (!output.createNewFile())) {
        throw new RuntimeException("Unable to create file, please check your folder persmissions " + output.getPath());
      }

      writer = new FileWriter(output);
      BufferedWriter out = new BufferedWriter(writer);
      out.write(format(value));
      out.flush(); return;
    } catch (IOException ex) {
      Logger.getLogger(OutputProject.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(OutputProject.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static QName getQName(Node n, String value) {
    String prefix = value.substring(0, value.indexOf(':'));
    String nsURI = n.lookupNamespaceURI(prefix);
    return new QName(nsURI, value.substring(value.indexOf(':') + 1));
  }

  /*public static String getEndpointNameForMyRole(BPELDocument.PartnerLink p) {
    String endpointName = null;
    if (p.getMyRole() != null) {
      endpointName = p.getMyRole() + "_myRole";
    }
    return endpointName;
  }

  public static String getEndpointNameForPartnerRole(BPELDocument.PartnerLink p) {
    String endpointName = null;
    if (p.getPartnerRole() != null) {
      endpointName = p.getPartnerRole() + "_partnerRole";
    }
    return endpointName;
  }*/

  public static String format(String unformattedXml) {
    try {
      Document document = parseXmlFile(unformattedXml);
      OutputFormat format = new OutputFormat(document);
      format.setLineWidth(65);
      format.setIndenting(true);
      format.setIndent(2);
      Writer out = new StringWriter();
      XMLSerializer serializer = new XMLSerializer(out, format);
      serializer.serialize(document);
      return out.toString();
    }
    catch (Throwable ignore) {}


    return unformattedXml;
  }

  private static Document parseXmlFile(String in) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      InputSource is = new InputSource(new StringReader(in));
      return db.parse(is);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
