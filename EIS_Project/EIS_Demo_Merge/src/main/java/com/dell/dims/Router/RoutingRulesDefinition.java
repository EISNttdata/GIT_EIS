package com.dell.dims.Router;

import com.dell.dims.Model.Activity;
import com.dell.dims.Model.ClassParameter;

import java.util.List;

/**
 * Created by Manoj_Mehta on 2/1/2017.
 */
public class RoutingRulesDefinition {


    private String ruleName;
    private String ruleDefinition;
    private String comments;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDefinition() {
        return ruleDefinition;
    }

    public void setRuleDefinition(String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    /**Returns Routing route
     * @param activity
     * @return
     */
    public StringBuffer getRoutingRule(Activity activity)
    {
       List<ClassParameter> listParams =  activity.getParameters();
        StringBuffer routeString =new StringBuffer("");
        for(ClassParameter param : listParams)
        {
              ClassParameter rNode = getRouteNode(param,"choose");
              if(rNode !=null)
              {
                  //choose =>
                        //when(N) =>
                            //Condition(V)
                                //=> Special Option(expected value)
                  processChoiceNode(rNode,routeString);
              }
        }
        return routeString;
    }

    /* from(anEndpoint)
    .choice()
    .when(someCondition)
    .to(firstEndpoint)
    .when(anotherCondition)
    .to(secondEndpoint)
  .otherwise()
    .to(thirdEndpoint)
  .end();              */
    private StringBuffer processChoiceNode(ClassParameter rNode, StringBuffer routeString) {
        List<ClassParameter> childParams = rNode.getChildProperties();
        routeString.append("\n.choice()");
        // get Name and value
        for(ClassParameter chParam : childParams) {
           // System.out.println(" node: Name : " + chParam.getName());
            if (chParam.getName().equalsIgnoreCase("otherwise"))
            {
                routeString.append("\n");
                routeString.append(".otherwise(");
                handleOtherwiseNode(chParam, routeString);// handle otherwise case
                routeString.append(")");
            }
            // handle when
            else
            {
                //routeString.append("."+chParam.getName() + "(" + chParam.getDefaultValue() + ")\n"); //.when(someCondition)
                routeString.append("\n");
                routeString.append("."+chParam.getName());//.when
                routeString.append ("(" + chParam.getDefaultValue() + ")"); //.when(someCondition)
                routeString.append("\n\t");
                routeString.append(chParam.getSpecialOption());

          /*      List<ClassParameter> gchildParams = chParam.getChildProperties(); // OutfileName node
                  //get Name and value for .to()
                for (ClassParameter gchParam : gchildParams)
                {
                    // node whose value to be capture for .to()
                    List<ClassParameter> valueParams = gchParam.getChildProperties();
                    for (ClassParameter valParam : valueParams)
                    {
                        routeString.append(".to("+gchParam.getName()+":" + valParam.getDefaultValue() + ")\n");   //.to(firstEndpoint)
                    }
                }*/
            }
        }
        return routeString;
    }

    /***
     * @param childParam
     * @param routeString
     * @return
     */
    private StringBuffer handleOtherwiseNode(ClassParameter childParam, StringBuffer routeString)
    {
        if(childParam.getChildProperties()!=null)
        {
         //check for "if"
         for(ClassParameter param : childParam.getChildProperties())
         {
             if(param.getName().equalsIgnoreCase("if"))
             {
                 routeString.append("if("+param.getDefaultValue()+")\n");

                 // OutfileName node
                 List<ClassParameter> gchildParams = param.getChildProperties();
                 // get Name and value
                 for(ClassParameter gchParam : gchildParams)
                 {
                     //System.out.println("file Name " + gchParam.getName());
                     // node whose value to be capture
                     if(gchParam.getChildProperties()!=null)
                     {
                         List<ClassParameter> valueParams = gchParam.getChildProperties();
                         for (ClassParameter valParam : valueParams)
                         {
                         //    routeString.append(".to("+gchParam.getName()+":" + valParam.getDefaultValue() + ")\n");   //.to(firstEndpoint)
                             routeString.append(gchParam.getName());
                             routeString.append("=");
                             routeString.append(valParam.getDefaultValue());
                         }
                     }
                     else
                     {
                         //routeString.append(".to("+param.getName()+":" + gchParam.getDefaultValue() + ")\n");
                         routeString.append(param.getName());
                         routeString.append("=");
                         routeString.append(gchParam.getDefaultValue());
                     }
                 }
             }
             //otherwise containing choose
             else if(param.getName().equalsIgnoreCase("choose"))
             {
                 processChoiceNode(param,routeString);
             }
             //otherwise contains no nested condition statements like choose,if
             else
             {
                 for(ClassParameter gchParam : param.getChildProperties())
                 {
                    // routeString.append(".to("+param.getName()+":" + gchParam.getDefaultValue() + ")\n");
                     routeString.append(param.getName());
                     routeString.append("=");
                     routeString.append(gchParam.getDefaultValue());
                 }
             }
         }
        }
        return routeString;
    }

    /***Return the node if values found
     * @param param
     * @param valueToSearch
     * @return
     */
    public ClassParameter getRouteNode(ClassParameter param, String valueToSearch)
    {
        if(param.getName().equalsIgnoreCase(valueToSearch))
        {
            return param;
        }
        else if(param.getChildProperties()!=null && param.getChildProperties().size()>0)
        {
        // check in child nodes
            for(ClassParameter chParam : param.getChildProperties())
            {
                if(chParam.getName()!=null) {
                    if (chParam.getName().equalsIgnoreCase(valueToSearch)) {
                        return chParam;
                    } else {
                        getRouteNode(chParam, valueToSearch);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param param
     * @return
     */
    public StringBuilder getValueOfLastNode(ClassParameter param,StringBuilder value)
    {
        if(param.getChildProperties()==null || param.getChildProperties().size()==0)
        {
            value.append(param.getDefaultValue());
            //return value;
        }
        else
        {
            for(ClassParameter chParam : param.getChildProperties())
            {
                getValueOfLastNode(chParam,value);
            }
        }

        return value;
    }
}
