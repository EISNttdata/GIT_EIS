package soa.output.Transformations;

import java.util.List;

/**
 * Created by Manoj_Mehta on 5/3/2017.
 */
public class TransformationGenerator
{

    private String name;
    private List<MapperSource> mapperSourceList;
    private List<MapperTarget> mapperTargetList;
    private String xslTemplateContent;

    public String getXslTemplateContent() {
        return xslTemplateContent;
    }

    public void setXslTemplateContent(String xslTemplateContent) {
        this.xslTemplateContent = xslTemplateContent;
    }

    public List<MapperSource> getMapperSourceList() {
        return mapperSourceList;
    }

    public void setMapperSourceList(List<MapperSource> mapperSourceList) {
        this.mapperSourceList = mapperSourceList;
    }

    public List<MapperTarget> getMapperTargetList() {
        return mapperTargetList;
    }

    public void setMapperTargetList(List<MapperTarget> mapperTargetList) {
        this.mapperTargetList = mapperTargetList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String generateTransformation()
    {
        StringBuffer buffer= new StringBuffer();

        buffer.append("" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<xsl:stylesheet version=\"1.0\"\n" +
                "\t\t\t xmlns:mhdr=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction\"\n" +
                "\t\t\t xmlns:ns0=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/"+getName()+"\"\n" +
                "\t\t\t xmlns:oraext=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc\"\n" +
                "\t\t\t xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "\t\t\t xmlns:xp20=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20\"\n" +
                "\t\t\t xmlns:xref=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions\"\n" +
                "\t\t\t xmlns:socket=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator\"\n" +
                "\t\t\t xmlns:oracle-xsl-mapper=\"http://www.oracle.com/xsl/mapper/schemas\"\n" +
                "\t\t\t xmlns:dvm=\"http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue\"\n" +
                //can have Dynamic content
                "\t\t\t xmlns:ns1=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/GlobalVariable\"\n" +
                "\t\t\t xmlns:tns=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\"\n" +

                "\t\t\t xmlns:oraxsl=\"http://www.oracle.com/XSL/Transform/java\"\n" +
                "\t\t\t xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
                "\t\t\t exclude-result-prefixes=\"xsd oracle-xsl-mapper xsi xsl ns0 ns1 tns mhdr oraext xp20 xref socket dvm oraxsl\"\n" +
                "\t\t\t xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n" +
                "\t\t\t xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\">\n");

        buffer.append(
                "  <oracle-xsl-mapper:schema>\n" +
                "    <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->\n" +
                "    <oracle-xsl-mapper:mapSources>\n");

        //First element added as empty so that full path is available
        buffer.append(
                "   <oracle-xsl-mapper:source type=\"WSDL\">\n" +
                "        <oracle-xsl-mapper:schema location=\"../WSDLs/BPELProcess1.wsdl\"/>\n" +
                "        <oracle-xsl-mapper:rootElement name=\"empty\"\n" +
                "                                       namespace=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/BPELProcess1\"/>\n"

        );
    for(MapperSource source : this.getMapperSourceList())
    {
        buffer.append(  "      <oracle-xsl-mapper:source type=\""+source.getSourceType()+"\">\n" +
                        "        <oracle-xsl-mapper:schema location=\""+source.getSchemaLocation()+"\"/>\n" +
                        "        <oracle-xsl-mapper:rootElement name=\""+source.getRootElementName()+"\"\n" +
                        "                                       namespace=\""+source.getRootElementNamespace()+"\"");
            if(source.getParamName()!=null)
            {
                buffer.append("\n <oracle-xsl-mapper:param name=\"_globalVariables\"");
            }

            buffer.append("/>\n");
    }
        buffer.append("    <oracle-xsl-mapper:mapTargets>\n");
        for(MapperTarget target : this.getMapperTargetList())
        {
            buffer.append(
                    "      <oracle-xsl-mapper:target type=\"" + target.getTargetType() + "\">\n" +
                    "        <oracle-xsl-mapper:schema location=\"" + target.getTargetLocation() + "\"/>\n" +
                    "        <oracle-xsl-mapper:rootElement name=\"" + target.getRootElementName() + "\"\n" +
                    "                                       namespace=\"" + target.getRootElementNamespace() + "\"/>\n" +
                    "      </oracle-xsl-mapper:target>\n");
        }
        buffer.append(    "    </oracle-xsl-mapper:mapTargets>\n" +
                "    <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.0.0(XSLT Build 151013.0700.0085) AT [FRI APR 28 13:47:50 IST 2017].-->\n" +
                "  </oracle-xsl-mapper:schema>\n");


        buffer.append(
                "  <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->\n" +
                "  <xsl:param name=\"_globalVariables\"/>\n" +
                "  <xsl:template match=\"/\">\n" +
                "  "+ this.getXslTemplateContent()+"\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>\n");

        return buffer.toString();
    }

    /*
    Inner Class having Mapper Source attributes
     */
     public class MapperSource
    {
        private String sourceType;
        private String schemaLocation;
        private String rootElementName;
        private String rootElementNamespace;
        private String  paramName;

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getSchemaLocation() {
            return schemaLocation;
        }

        public void setSchemaLocation(String schemaLocation) {
            this.schemaLocation = schemaLocation;
        }

        public String getRootElementName() {
            return rootElementName;
        }

        public void setRootElementName(String rootElementName) {
            this.rootElementName = rootElementName;
        }

        public String getRootElementNamespace() {
            return rootElementNamespace;
        }

        public void setRootElementNamespace(String rootElementNamespace) {
            this.rootElementNamespace = rootElementNamespace;
        }
    }

    /**
     * Mapper Destination
     */

    public class MapperTarget
    {
        private String targetType;
        private String targetLocation;
        private String rootElementName;
        private String rootElementNamespace;


        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public String getTargetLocation() {
            return targetLocation;
        }

        public void setTargetLocation(String targetLocation) {
            this.targetLocation = targetLocation;
        }

        public String getRootElementName() {
            return rootElementName;
        }

        public void setRootElementName(String rootElementName) {
            this.rootElementName = rootElementName;
        }

        public String getRootElementNamespace() {
            return rootElementNamespace;
        }

        public void setRootElementNamespace(String rootElementNamespace) {
            this.rootElementNamespace = rootElementNamespace;
        }
    }
}
