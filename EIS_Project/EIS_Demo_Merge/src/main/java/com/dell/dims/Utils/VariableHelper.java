
package com.dell.dims.Utils;

public class VariableHelper   
{
    public static String toVariableName(String variableNameToFormat) throws Exception {
        if (variableNameToFormat==null || variableNameToFormat=="")
        {
            return "";
        }
         
        variableNameToFormat = toSafeType(variableNameToFormat);
        variableNameToFormat = removeSpecialChar(variableNameToFormat);
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
        String firstCharOfTheVariable = variableNameToFormat.substring(0, 1);
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
        String endOfTheVariable = variableNameToFormat.substring(1, variableNameToFormat.length()- 1);
        return (firstCharOfTheVariable.toLowerCase() + endOfTheVariable).replace(" ", "");
    }

    private static String removeSpecialChar(String variableNameToFormat) throws Exception {
        variableNameToFormat = variableNameToFormat.replace("-", "_");
        variableNameToFormat = variableNameToFormat.replace("&", "");
        return variableNameToFormat;
    }

    public static String toClassName(String name) throws Exception {
        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
        String className = name.substring(0, 1).toUpperCase() + name.substring(1, name.length() - 1).replace(" ", "");
        className = removeSpecialChar(className);
        return className;
    }

    public static String toSafeType(String variableNameToFormat) throws Exception {
        if (variableNameToFormat==null || variableNameToFormat=="")
        {
            return "";
        }
         
        if (StringSupport.equals(variableNameToFormat, "interface"))
        {
            return "@interface";
        }
         
        if (StringSupport.equals(variableNameToFormat, "object"))
        {
            return "@object";
        }
         
        if (StringSupport.equals(variableNameToFormat, "param"))
        {
            return "@param";
        }
         
        if (StringSupport.equals(variableNameToFormat, "params"))
        {
            return "@params";
        }
         
        /*if (variableNameToFormat[0] >= '0' && variableNameToFormat[0] <= '9')
        {
            return "a" + variableNameToFormat;
        }*/
         
        return variableNameToFormat;
    }

    public static String toSafeType(String parent, String variableNameToFormat) throws Exception {

        String variableNameToLowerCase = removeSpecialChar(variableNameToFormat).toLowerCase();
        if (parent != null && (StringSupport.equals(variableNameToLowerCase, "interface") || StringSupport.equals(variableNameToLowerCase, "object") || StringSupport.equals(variableNameToLowerCase, "param") || StringSupport.equals(variableNameToLowerCase, "params") || StringSupport.equals(variableNameToLowerCase, "subtypeinfos") || StringSupport.equals(variableNameToLowerCase, "keys")))
        {
            return parent + toClassName(variableNameToFormat);
        }
         
        return toSafeType(variableNameToFormat);
    }

}


/**            if (variableNameToFormat[0] >= '0' && variableNameToFormat[0] <= '9')
            {
                return "a" + variableNameToFormat;
            }*/