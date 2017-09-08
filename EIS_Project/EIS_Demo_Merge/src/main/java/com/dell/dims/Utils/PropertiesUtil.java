package com.dell.dims.Utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Manoj_Mehta on 12/28/2016.
 */
public class PropertiesUtil {

   static String propFileName = "tibco.properties";
    public static Properties props;

    public static Properties getPropertyFile() throws IOException {

        Resource resource = new ClassPathResource(propFileName);
        props = PropertiesLoaderUtils.loadProperties(resource);
        PropertiesUtil.setProps(props);

      /*
        if(props==null)
        {
            props = new Properties();
            InputStream inputStream;

            PropertiesUtil util=new PropertiesUtil();

           inputStream = util.getClass().getClassLoader().getResourceAsStream(File.separator+propFileName);

            //inputStream = new FileInputStream(propFileName);
            if (inputStream != null) {
                props.load(inputStream);
            } else
            {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        }*/

        return props;
    }

    public static Properties getPropertyFile(String filePath) throws IOException {
        Properties props = new Properties();
        InputStream inputStream;

        PropertiesUtil util=new PropertiesUtil();

        inputStream = util.getClass().getClassLoader().getResourceAsStream(filePath);

        if (inputStream != null) {
            props.load(inputStream);
        } else
        {
            throw new FileNotFoundException("property file '" + filePath + "' not found in the classpath");
        }

        return props;
    }

    public static void setProps(Properties props) {
        PropertiesUtil.props = props;
    }
}
