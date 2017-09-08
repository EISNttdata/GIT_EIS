package com.dell.dims.Utils;


import com.sun.codemodel.JCodeModel;
import com.sun.org.apache.xerces.internal.xs.XSImplementation;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import com.sun.xml.bind.api.impl.NameConverter;
import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.dom.DOMXSImplementationSourceImpl;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.xs.*;
import org.w3c.dom.ls.LSInput;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Pramod_Kumar_Tyagi on 6/18/2017.
 */
public class XSDUtils {


  /**
   * Load an XSD file
   */
  void loadSchema (String xsdURI)
  {
    XSImplementation impl = (XSImplementation)
      (new DOMXSImplementationSourceImpl()).getDOMImplementation ("XS-Loader");

    XSLoader schemaLoader = (XSLoader) impl.createXSLoader (null);

    XSModel xsModel = schemaLoader.loadURI (xsdURI);
  }

  /**
   * Process schema content
   */
  private void processXSModel (XSModel xsModel)
  {
    XSNamedMap xsMap;

    // process model group definitions
    xsMap = xsModel.getComponents (XSConstants.MODEL_GROUP_DEFINITION);
    for (int i = 0; i < xsMap.getLength(); i ++)
    {
      XSModelGroupDefinition xsGroupDef = (XSModelGroupDefinition) xsMap.item (i);
      XSModelGroup xsGroup = xsGroupDef.getModelGroup();

    }

    // process top-level type definitions
    xsMap = xsModel.getComponents (XSConstants.TYPE_DEFINITION);
    for (int i = 0; i < xsMap.getLength(); i ++)
    {
      XSTypeDefinition xsTDef = (XSTypeDefinition) xsMap.item (i);
      processXSTypeDef (xsTDef);
    }

    // process top-level element declarations
    xsMap = xsModel.getComponents (XSConstants.ELEMENT_DECLARATION);
    for (int i = 0; i < xsMap.getLength(); i ++)
    {
      XSElementDeclaration xsElementDecl = (XSElementDeclaration) xsMap.item (i);
  //    processXSElementDecl (xsElementDecl);
    }
  }

  /**
   * Process type definition
   */
  private void processXSTypeDef (XSTypeDefinition xsTDef)
  {
    switch (xsTDef.getTypeCategory())
    {
      case XSTypeDefinition.SIMPLE_TYPE:

     //   processXSSimpleType ((XSSimpleTypeDefinition) xsTDef);
        break;

      case XSTypeDefinition.COMPLEX_TYPE:

        XSComplexTypeDefinition xsCTDef = (XSComplexTypeDefinition) xsTDef;

        // element's attributes
        XSObjectList xsAttrList = xsCTDef.getAttributeUses();
        for (int i = 0; i < xsAttrList.getLength(); i ++)
        {
       //   processXSAttributeUse ((XSAttributeUse) xsAttrList.item (i));
        }

        // element content
        switch (xsCTDef.getContentType())
        {
          case XSComplexTypeDefinition.CONTENTTYPE_EMPTY:

            break;

          case XSComplexTypeDefinition.CONTENTTYPE_SIMPLE:

         //   parseValueType (xsCTDef.getSimpleType());
            break;

          case XSComplexTypeDefinition.CONTENTTYPE_ELEMENT:

            processXSParticle (xsCTDef.getParticle());
            break;

          case XSComplexTypeDefinition.CONTENTTYPE_MIXED:


            processXSParticle (xsCTDef.getParticle());
            break;
        }
    }


  }

  /**
   * Process particle
   */
  private void processXSParticle (XSParticle xsParticle)
  {
    XSTerm xsTerm = xsParticle.getTerm();
    switch (xsTerm.getType())
    {
      case XSConstants.ELEMENT_DECLARATION:

     //   processXSElementDecl ((XSElementDeclaration) xsTerm);
        break;

      case XSConstants.MODEL_GROUP:

        // this is one of the globally defined groups
        // (found in top-level declarations)

        XSModelGroup xsGroup = (XSModelGroup) xsTerm;

        // it also consists of particles
        XSObjectList xsParticleList = xsGroup.getParticles();
        for (int i = 0; i < xsParticleList.getLength(); i ++)
        {
          processXSParticle ((XSParticle) xsParticleList.item (i));
        }


        break;

      case XSConstants.WILDCARD:


        break;
    }
  }

  private static XSModel getSchema(String schemaText) throws ClassNotFoundException,
    InstantiationException, IllegalAccessException, ClassCastException {
    final InputStream stream = new ByteArrayInputStream(schemaText.getBytes(StandardCharsets.UTF_8));
    final LSInput input = new DOMInputImpl();
    input.setByteStream(stream);

    final XMLSchemaLoader xsLoader = new XMLSchemaLoader();
    return xsLoader.load(input);
  }

  public static void xjcCreateJavaClassesFromXSD(File schemaFile_in, File directory) throws IOException {
    SchemaCompiler sc = XJC.createSchemaCompiler();
    //File file = new File(getFilePathToWrite("SCA-INF/src",null)+ File.separator + fileName);
    // File out = new File("out");
    InputSource is = new InputSource(schemaFile_in.toURI().toString());

    // Parse & build
    sc.parseSchema(is);
    try
    {
      S2JJAXBModel model = sc.bind();
      JCodeModel jCodeModel = model.generateCode(null, null);
      jCodeModel.build(directory);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public static String convertToPackageName(String xmlNamespace) {
    NameConverter nameConverter = new NameConverter.Standard();
    return nameConverter.toPackageName(xmlNamespace);
  }
}
