package soa.output.jca;

import soa.model.AdapterConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class JcaFileGenerator extends AdapterConfig
{
  //  String adapter="file";
  //  String wsdlLoc="\"../WSDLs/";
 //   String xmlns="http://platform.integration.oracle/blocks/adapter/fw/metadata";

    public String generatePollerJCA(AdapterConfig adpater)
    {
        StringBuffer buffer=new StringBuffer();
/* <adapter-config name="PollNewFiles" adapter="file" wsdlLocation="../WSDLs/PollNewFiles.wsdl" xmlns="http://platform.integration.oracle/blocks/adapter/fw/metadata">
  <connection-factory UIincludeWildcard="*.270|*.276" location="eis/HAFileAdapter"/>
  <endpoint-activation portType="Read_ptt" operation="Read">
    <activation-spec className="oracle.tip.adapter.file.inbound.FileActivationSpec">
      <property name="LogicalDirectory" value="POLL_DIR"/>
      <property name="UseHeaders" value="false"/>
      <property name="MinimumAge" value="1"/>
      <property name="Recursive" value="true"/>
      <property name="MaxRaiseSize" value="1"/>
      <property name="PollingFrequency" value="1"/>
      <property name="DeleteFile" value="false"/>
      <property name="IncludeFiles" value=".*\.270|.*\.276"/>
    </activation-spec>
  </endpoint-activation>

</adapter-config>*/
        buffer.append("<adapter-config name=\""+adpater.getActivityName() +"\" adapter=\""+adpater.getAdapter()+"\" wsdlLocation=\""+adpater.getWsdlLocation()+adpater.getActivityName()+"\"");
        buffer.append(" xmlns=\"http://platform.integration.oracle/blocks/adapter/fw/metadata\">\n");
        buffer.append("<connection-factory location=\""+adpater.getAdapterLocation()+"\" UIincludeWildcard=\"" + adpater.getIncludeWilcard()+"\"/>\n");
        buffer.append("<endpoint-activation portType=\""+adpater.getOperationName()+"_ptt\" operation=\""+adpater.getOperationName()+"\">\n");
        buffer.append(" <activation-spec className=\"oracle.tip.adapter.file.inbound.FileActivationSpec\">\n");
        // add Properties
        HashMap<String,String> propertiesMap= (HashMap<String, String>) adpater.getProperties();
        for (Map.Entry<String, String> map : propertiesMap.entrySet())
        {
            buffer.append("     <property name=\""+map.getKey()+"\" value=\""+map.getValue()+"\"/>\n");
        }
        buffer.append(" </activation-spec>\n");
        buffer.append("</endpoint-activation>\n");
        buffer.append("</adapter-config>\n");

        return buffer.toString();
    }

    /**
     *
     * Generate JCA For ListFile
     */
    public String generateListFileJCA(AdapterConfig adapter)
    {
        // add Properties
        HashMap<String,String> propertiesMap= (HashMap<String, String>) adapter.getProperties();
        String wilscardValue="*";
        for (Map.Entry<String, String> map : propertiesMap.entrySet())
        {
            if(map.getKey().equalsIgnoreCase("IncludeFiles"))
            {
                wilscardValue=map.getValue();
            }
        }
        StringBuilder builderSrtr=new StringBuilder();
        builderSrtr.append("<adapter-config name=\""+adapter.getActivityName() +"\" adapter=\""+adapter.getAdapter()+"\" wsdlLocation=\""+adapter.getWsdlLocation()+adapter.getActivityName()+".wsdl\"\n");
        builderSrtr.append("xmlns=\"http://platform.integration.oracle/blocks/adapter/fw/metadata\">\n");
        builderSrtr.append(" <connection-factory location=\"eis/"+adapter.getAdapterLocation()+"\" UIincludeWildcard=\"" + wilscardValue + "\"/>\n");
        builderSrtr.append(" <endpoint-interaction portType=\""+adapter.getOperationName()+"_ptt\" operation=\""+adapter.getOperationName()+"\">\n");
        builderSrtr.append(" <interaction-spec className=\"oracle.tip.adapter.file.outbound.FileListInteractionSpec\">\n");

        for (Map.Entry<String, String> map : propertiesMap.entrySet())
        {
            builderSrtr.append("     <property name=\""+map.getKey()+"\" value=\""+map.getValue()+"\"/>\n");
        }
        builderSrtr.append(" </interaction-spec>\n");
        builderSrtr.append(" </endpoint-interaction>\n");
        builderSrtr.append("</adapter-config>\n");

        return builderSrtr.toString();
        /*
 <adapter-config name="ListFiles" adapter="file" wsdlLocation="../WSDLs/ListFiles.wsdl" xmlns="http://platform.integration.oracle/blocks/adapter/fw/metadata">

  <connection-factory location="eis/FileAdapter" UIincludeWildcard="*.270"/>
  <endpoint-interaction portType="FileListing_ptt" operation="FileListing">
    <interaction-spec className="oracle.tip.adapter.file.outbound.FileListInteractionSpec">
      <property name="PhysicalDirectory" value="C:\apps\tibco_n2vdev009\TEST\nehen"/>
      <property name="Recursive" value="true"/>
      <property name="IncludeFiles" value=".*\.270"/>
    </interaction-spec>
  </endpoint-interaction>

</adapter-config>
         */
    }

    /**
     *
     * Generate JCA For FTPPut
     */
    public String generateFTPPutJCA(AdapterConfig adapter)
    {
        // add Properties
        HashMap<String,String> propertiesMap= (HashMap<String, String>) adapter.getProperties();
        String wilscardValue="*";
        for (Map.Entry<String, String> map : propertiesMap.entrySet())
        {
            if(map.getKey().equalsIgnoreCase("IncludeFiles"))
            {
                wilscardValue=map.getValue();
            }
        }
        StringBuilder builderSrtr=new StringBuilder();
        builderSrtr.append("<adapter-config name=\""+adapter.getActivityName() +"\" adapter=\""+adapter.getAdapter()+"\" wsdlLocation=\""+adapter.getWsdlLocation()+adapter.getActivityName()+".wsdl\"\n");
        builderSrtr.append("xmlns=\"http://platform.integration.oracle/blocks/adapter/fw/metadata\">\n");
        builderSrtr.append(" <connection-factory location=\"eis/"+adapter.getAdapterLocation()+"\"/>\n");
        builderSrtr.append(" <endpoint-interaction portType=\""+adapter.getOperationName()+"_ptt\" operation=\""+adapter.getOperationName()+"\">\n");
        builderSrtr.append(" <interaction-spec className=\"oracle.tip.adapter.ftp.outbound.FTPInteractionSpec\">\n");

        for (Map.Entry<String, String> map : propertiesMap.entrySet())
        {
            builderSrtr.append("     <property name=\""+map.getKey()+"\" value=\""+map.getValue()+"\"/>\n");
        }
        builderSrtr.append(" </interaction-spec>\n");
        builderSrtr.append(" </endpoint-interaction>\n");
        builderSrtr.append("</adapter-config>\n");

        return builderSrtr.toString();
        /*
  <adapter-config name="FTPPut" adapter="ftp" wsdlLocation="../WSDLs/FTPPut.wsdl" xmlns="http://platform.integration.oracle/blocks/adapter/fw/metadata">

  <connection-factory location="eis/Ftp/FtpAdapter"/>
  <endpoint-interaction portType="Put_ptt" operation="Put">
    <interaction-spec className="oracle.tip.adapter.ftp.outbound.FTPInteractionSpec">
      <property name="PhysicalDirectory" value="C:\Users\manoj_mehta\Desktop\FTP"/>
      <property name="FileType" value="ascii"/>
      <property name="Append" value="false"/>
      <property name="FileNamingConvention" value="*.270"/>
      <property name="NumberMessages" value="1"/>
    </interaction-spec>
  </endpoint-interaction>

</adapter-config>
         */
    }
}
