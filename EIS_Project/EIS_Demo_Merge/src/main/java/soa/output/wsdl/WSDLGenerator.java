package soa.output.wsdl;

/**
 * Created by Manoj_Mehta on 4/7/2017.
 */
public class WSDLGenerator
{
    private String activityName;
    private String targetNamespace;
  /*  private String partnerLinkTypeName;
    private String role;
    private String portType;

    private String messageName;
    private String partName;
    private String partElement;
*/
    private String operationName;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    /**
      * Return respective WSDL Generator class
      * */
   /* public WSDLGenerator getWsdlGenerator(String activityName)
    {
        if(activityName.equalsIgnoreCase("PollNewFiles"))
        {
            return new PollNewFilesWSDL();
        }
        return this;
    }*/

    public String generateWSDL()
    {
        StringBuilder buffer=new StringBuilder("");

        buffer.append("<wsdl:definitions");
        buffer.append("\t\t name=\"" + this.getActivityName() + "\"\n");
        buffer.append("\t\t targetNamespace=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:jca=\"http://xmlns.oracle.com/pcbpel/wsdl/jca/\"\n");
        buffer.append("\t\t xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n");
        buffer.append("\t\t xmlns:tns=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:opaque=\"http://xmlns.oracle.com/pcbpel/adapter/opaque/\"\n");
        buffer.append("\t\t xmlns:pc=\"http://xmlns.oracle.com/pcbpel/\"\n");
        buffer.append("\t\t xmlns:plt=\"http://schemas.xmlsoap.org/ws/2003/05/partner-link/\"\n");
        buffer.append(">\n");
        buffer.append("<plt:partnerLinkType name=\"" + this.getOperationName() + "_plt\" >\n");
        buffer.append("\t <plt:role name=\"" + this.getOperationName() + "_role\" >\n");
        buffer.append("\t\t <plt:portType name=\"tns:" + this.getOperationName() + "_ptt\" />\n");
        buffer.append("\t </plt:role>\n");
        buffer.append("</plt:partnerLinkType>\n");

        buffer.append("<wsdl:types>\n");

        //Need to add requird code
        buffer.append("<xsd:schema>\n");
        //buffer.append("        xmlns=\"http://www.w3.org/2001/XMLSchema\" >\n");
        //buffer.append("  <element name=\"opaqueElement\" type=\"base64Binary\" />\n");
        buffer.append("<xsd:import namespace=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\"\n" +
                "                 schemaLocation=\"../Schemas/ListFiles_Input.xsd\"/>");
        buffer.append("</xsd:schema>\n");

        // <xsd:import namespace="http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input"
        // schemaLocation="../Schemas/ListFiles_Input.xsd"/>


        buffer.append("</wsdl:types>\n");

        /*buffer.append("<wsdl:message name=\"" + this.getOperationName() + "_msg\">\n");
        buffer.append("    <wsdl:part name=\"opaque\" element=\"opaque:opaqueElement\"/>\n");
        buffer.append("</wsdl:message>\n");
        */
        buffer.append("" +
                "<wsdl:message name=\"invokeInput\">\n" +
                "\t\t<wsdl:part name=\"parameters\" element=\"proj:javaCodeActivityInput\"/>\n" +
                "</wsdl:message>\n" +
                "<wsdl:message name=\"invokeOutput\">\n" +
                "\t\t<wsdl:part name=\"parameters\" element=\"proj:javaCodeActivityOutput\"/>\n" +
                "</wsdl:message>" +
                "");

       /* buffer.append("<wsdl:portType name=\"" + this.getOperationName() + "_ptt\">\n");
        buffer.append("    <wsdl:operation name=\"" + this.getOperationName() + "\">\n");
        buffer.append("        <wsdl:input message=\"tns:" + this.getOperationName() + "_msg\"/>\n");
        buffer.append("    </wsdl:operation>\n");
        buffer.append("</wsdl:portType>\n");*/

        buffer.append(
                " <wsdl:portType name=\"IlistFilesInFolders\">\n" +
                        "\t<wsdl:operation name=\"invoke\">\n" +
                        "\t\t<wsdl:input message=\"tns:invokeInput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\" ns1:Action=\"\"/>\n" +
                        "\t\t<wsdl:output message=\"tns:invokeOutput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\" ns1:Action=\"\"/>\n" +
                        "\t</wsdl:operation>\n" +
                        "</wsdl:portType>\n");
        buffer.append("</wsdl:definitions>\n");

        return buffer.toString();
    }


    /**
     * @return String
     */

public String generateWSDLForMainProcess()
{
    StringBuilder buffer=new StringBuilder("");

    buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            buffer.append("<wsdl:definitions");
            buffer.append("  name=\"" + this.getActivityName() + "\"\n");
            buffer.append("\t\ttargetNamespace=\"" + this.getTargetNamespace() + "\"\n" +
                          "\t\txmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n" +
                          "\t\txmlns:client=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/" +this.getActivityName()+"\"\n" +
                          "\t\txmlns:proj=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\""+
                           "\t\t xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\""+
                           "\t\txmlns:ns1=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/GlobalVariable\">");

            buffer.append("\n\n");

     buffer.append("<plnk:partnerLinkType name=\""+this.getActivityName()+"\">\n" +
            "\t\t<plnk:role name=\""+this.getActivityName()+"Provider\" portType=\"client:"+this.getActivityName()+"\"/>\n" +
            "\t\t<plnk:role name=\""+this.getActivityName()+"Requester\" portType=\"client:"+this.getActivityName()+"Callback\"/>\n" +
            "\t</plnk:partnerLinkType>\n" +
            "</wsdl:definitions>");

    buffer.append("\n\n<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "\tTYPE DEFINITION - List of services participating in this BPEL process\n" +
                    "\tThe default output of the BPEL designer uses strings as input and \n" +
                    "\toutput to the BPEL Process. But you can define or import any XML \n" +
                    "\tSchema type and use them as part of the message types.\n" +
                    "\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->  \n\n");

            buffer.append("<wsdl:types>\n" +
                    "\t\t<schema xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" >\n" +
                    "\t\txmlns:client=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/BPELProcess1\""+
                    "\t\txmlns:proj=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\"7"+
                    "\t\txmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\" xmlns:ns1=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/GlobalVariable\">"+
                    "\t\t\t<import namespace=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\" schemaLocation=\"../Schemas/ListFiles_Input.xsd\" />\n" +
                    "\t\t\t<import namespace=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/BPELProcess1\" schemaLocation=\"../Schemas/BPELProcess1.xsd\" />\n" +
                    "\t\t</schema>\n" +
                    "</wsdl:types>\n\n");

            buffer.append("<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "\tMESSAGE TYPE DEFINITION - Definition of the message types used as \n" +
            "\tpart of the port type defintions\n" +
            "\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->      \n\n");

            buffer.append("<wsdl:message name=\""+this.getActivityName()+"RequestMessage\">\n" +
           // "\t\t<wsdl:part name=\"payload\" element=\"client:"+this.operationName+"\"/>\n" +
            "\t\t<wsdl:part name=\"payload\" element=\"client:process\"/>\n" +
            "\t</wsdl:message>\n" +
            "\n" +
            "\t<wsdl:message name=\""+this.getActivityName()+"ResponseMessage\">\n" +
           // "\t\t<wsdl:part name=\"payload\" element=\"client:"+this.operationName+"Response\"/>\n" +
            "\t\t<wsdl:part name=\"payload\" element=\"proj:javaCodeActivityOutput\"/>\n" +
            "\t</wsdl:message>\n\n");

            buffer.append("<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "\tPORT TYPE DEFINITION - A port type groups a set of operations into\n" +
            "\ta logical service unit.\n" +
            "\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->");

            buffer.append("<!-- portType implemented by the "+this.getActivityName()+" BPEL process -->\n" +
            "\t<wsdl:portType name=\""+this.getActivityName()+"\">\n" +
            "\t\t<wsdl:operation name=\"process\">\n" +
            "\t\t\t<wsdl:input message=\"client:"+this.getActivityName()+"RequestMessage\"/>\n" +
            "\t\t</wsdl:operation>\n" +
            "\t</wsdl:portType>\n");

            buffer.append("<!-- portType implemented by the requester of "+this.getActivityName()+" BPEL process\n" +
                    "\tfor asynchronous callback purposes\n" +
                    "\t-->");

    buffer.append("<wsdl:portType name=\""+this.getActivityName()+"Callback\">\n" +
            "\t\t<wsdl:operation name=\"processResponse\">\n" +
            "\t\t\t<wsdl:input message=\"client:"+this.getActivityName()+"ResponseMessage\"/>\n" +
            "\t\t</wsdl:operation>\n" +
            "\t</wsdl:portType>");

    buffer.append("<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "\tPARTNER LINK TYPE DEFINITION\n" +
            "\t"+this.getActivityName()+" partnerLinkType binds the provider and\n" +
            "\trequester portType into an asynchronous conversation.\n" +
            "\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->\n");

    return buffer.toString();
}

    public String generateWSDL_IlistFilesInFolders()
    {
        StringBuilder buffer=new StringBuilder("");

        buffer.append("<wsdl:definitions");
        buffer.append("\t\t name=\"" + this.getActivityName() + "\"\n");
        buffer.append("\t\t targetNamespace=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:jca=\"http://xmlns.oracle.com/pcbpel/wsdl/jca/\"\n");
        buffer.append("\t\t xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n");
        buffer.append("\t\t xmlns:tns=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:opaque=\"http://xmlns.oracle.com/pcbpel/adapter/opaque/\"\n");
        buffer.append("\t\t xmlns:pc=\"http://xmlns.oracle.com/pcbpel/\"\n");
        buffer.append("\t\t xmlns:plt=\"http://schemas.xmlsoap.org/ws/2003/05/partner-link/\"\n");
        buffer.append(">\n");
        buffer.append("<plt:partnerLinkType name=\"" + this.getOperationName() + "_plt\" >\n");
        buffer.append("\t <plt:role name=\"" + this.getOperationName() + "_role\" >\n");
        buffer.append("\t\t <plt:portType name=\"tns:" + this.getOperationName() + "_ptt\" />\n");
        buffer.append("\t </plt:role>\n");
        buffer.append("</plt:partnerLinkType>\n");

        buffer.append("<wsdl:types>\n");

        //Need to add requird code
        buffer.append("<xsd:schema>\n");
        //buffer.append("        xmlns=\"http://www.w3.org/2001/XMLSchema\" >\n");
        //buffer.append("  <element name=\"opaqueElement\" type=\"base64Binary\" />\n");
        buffer.append("<xsd:import namespace=\"http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input\"\n" +
                "                 schemaLocation=\"../Schemas/ListFiles_Input.xsd\"/>");
        buffer.append("</xsd:schema>\n");

        // <xsd:import namespace="http://xmlns.oracle.com/IESN_BatchFileAdapter_27XApplication1/Test/ListFiles_Input"
        // schemaLocation="../Schemas/ListFiles_Input.xsd"/>


        buffer.append("</wsdl:types>\n");

        /*buffer.append("<wsdl:message name=\"" + this.getOperationName() + "_msg\">\n");
        buffer.append("    <wsdl:part name=\"opaque\" element=\"opaque:opaqueElement\"/>\n");
        buffer.append("</wsdl:message>\n");
        */
        buffer.append("" +
                "<wsdl:message name=\"invokeInput\">\n" +
                "\t\t<wsdl:part name=\"parameters\" element=\"proj:javaCodeActivityInput\"/>\n" +
                "</wsdl:message>\n" +
                "<wsdl:message name=\"invokeOutput\">\n" +
                "\t\t<wsdl:part name=\"parameters\" element=\"proj:javaCodeActivityOutput\"/>\n" +
                "</wsdl:message>" +
                "");

       /* buffer.append("<wsdl:portType name=\"" + this.getOperationName() + "_ptt\">\n");
        buffer.append("    <wsdl:operation name=\"" + this.getOperationName() + "\">\n");
        buffer.append("        <wsdl:input message=\"tns:" + this.getOperationName() + "_msg\"/>\n");
        buffer.append("    </wsdl:operation>\n");
        buffer.append("</wsdl:portType>\n");*/

        buffer.append(
                " <wsdl:portType name=\"IlistFilesInFolders\">\n" +
                        "\t<wsdl:operation name=\"invoke\">\n" +
                        "\t\t<wsdl:input message=\"tns:invokeInput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\" ns1:Action=\"\"/>\n" +
                        "\t\t<wsdl:output message=\"tns:invokeOutput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\" ns1:Action=\"\"/>\n" +
                        "\t</wsdl:operation>\n" +
                        "</wsdl:portType>\n");
        buffer.append("</wsdl:definitions>\n");

        return buffer.toString();
    }

    public String generateWSDL_IlistFilesInFoldersWrapper()
    {
        StringBuilder buffer=new StringBuilder("");

        buffer.append("<wsdl:definitions");
        buffer.append("\t\t name=\"" + this.getActivityName() + "\"\n");
        buffer.append("\t\t targetNamespace=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        buffer.append("\t\txmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"");
        buffer.append("\t\txmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"");
        buffer.append("\t\t xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n");
        buffer.append("\t\t xmlns:tns=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t>\n");

        buffer.append("\t<plnk:partnerLinkType name=\"listFilesInFolders.test\" >\n");
        buffer.append("\t<plnk:role name=\"listFilesInFolders\" >\n");
        buffer.append("\t\t <plnk:portType name=\"tns:listFilesInFolders.test\" />\n");
        buffer.append("\t </plnk:role>\n");
        buffer.append("\t</plnk:partnerLinkType>\n");
        buffer.append("\t<wsdl:import namespace=\"http://listFiles.CommonProcesses/\" location=\"IlistFilesInFolders.wsdl\"/>");
        buffer.append("</wsdl:definitions>");
        return buffer.toString();
    }

    public String generateWSDL_MyInterface()
    {
        StringBuilder buffer=new StringBuilder("");

        buffer.append("<wsdl:definitions");
        buffer.append("\t\t name=\"" + this.getActivityName() + "\"\n");
        buffer.append("\t\t targetNamespace=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
        buffer.append("\t\t xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap\"");
        buffer.append("\t\txmlns:soap12=\"http://schemas.xmlsoap.org/wsdl/soap12/\"");
        buffer.append("\t\txmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\"");
        buffer.append("\t\t xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n");
        buffer.append("\t\t xmlns:tns=\"" + this.getTargetNamespace() + "\"\n");
        buffer.append("\t\t>\n");

        buffer.append("<wsdl:types>\n");

        //Need to add requird code
        buffer.append("\t<xsd:schema>\n");
        buffer.append("\t\t<xsd:schema targetNamespace=\""+this.getTargetNamespace()+"\" xmlns:tns=\""+this.getTargetNamespace()+"\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
        buffer.append("\t\t<xsd:complexType name=\"doSomething\">");
        buffer.append("\t\t\t<xsd:sequence>\n" +
                "\t\t\t\t<xsd:element name=\"arg0\" type=\"xsd:string\"/>\n" +
                "\t\t\t</xsd:sequence>");
        buffer.append("</xsd:complexType>");

        buffer.append("<xsd:element name=\"doSomething\" type=\"tns:doSomething\"/>");
        buffer.append("<xsd:complexType name=\"doSomethingResponse\">");
        buffer.append("\t\t\t<xsd:sequence>");
        buffer.append("\t\t\t<xsd:element name=\"return\" type=\"xsd:string\"/>");
        buffer.append("\t\t\t</xsd:sequence>");
        buffer.append("</xsd:complexType>");
        buffer.append("<xsd:element name=\"doSomethingResponse\" type=\"tns:doSomethingResponse\"/>");
        buffer.append("\t\t</xsd:schema>");
        buffer.append("</wsdl:types>\n");

        /*buffer.append("<wsdl:message name=\"" + this.getOperationName() + "_msg\">\n");
        buffer.append("    <wsdl:part name=\"opaque\" element=\"opaque:opaqueElement\"/>\n");
        buffer.append("</wsdl:message>\n");
        */
        buffer.append(" <wsdl:message name=\"doSomethingInput\">\n" +
                "        <wsdl:part name=\"parameters\" element=\"tns:doSomething\"/>\n" +
                "    </wsdl:message>\n" +
                "    <wsdl:message name=\"doSomethingOutput\">\n" +
                "        <wsdl:part name=\"parameters\" element=\"tns:doSomethingResponse\"/>\n" +
                "    </wsdl:message>");

       /* buffer.append("<wsdl:portType name=\"" + this.getOperationName() + "_ptt\">\n");
        buffer.append("    <wsdl:operation name=\"" + this.getOperationName() + "\">\n");
        buffer.append("        <wsdl:input message=\"tns:" + this.getOperationName() + "_msg\"/>\n");
        buffer.append("    </wsdl:operation>\n");
        buffer.append("</wsdl:portType>\n");*/

        buffer.append(
                "<wsdl:portType name=\"MyInterface\">\n" +
                        "        <wsdl:operation name=\"doSomething\">\n" +
                        "            <wsdl:input message=\"tns:doSomethingInput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\"\n" +
                        "                 ns1:Action=\"\"/>\n" +
                        "            <wsdl:output message=\"tns:doSomethingOutput\" xmlns:ns1=\"http://www.w3.org/2006/05/addressing/wsdl\"\n" +
                        "                 ns1:Action=\"\"/>\n" +
                        "        </wsdl:operation>\n" +
                        "    </wsdl:portType>");

        buffer.append("</wsdl:definitions>\n");


        return buffer.toString();
    }

}
