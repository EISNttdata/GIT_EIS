
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUpdateActivity extends Activity
{
    public JdbcUpdateActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public JdbcUpdateActivity() throws Exception {
    }

    //private int timeOut;
    private String timeOut;
    private boolean commit;

    private boolean emptyStringAsNull;
    private String  statement;
    private String jdbcSharedConfig;
    /**
     * Gets or sets the query statement parameters.
     * Key is the parameter Name
     * Value is the parameter Type (VARCHAR, INT, ...)
     * The query statement parameters.
     */
    private Map<String, String> prepared_Param_DataType;

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public boolean isEmptyStringAsNull() {
        return emptyStringAsNull;
    }

    public void setEmptyStringAsNull(boolean emptyStringAsNull) {
        this.emptyStringAsNull = emptyStringAsNull;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getJdbcSharedConfig() {
        return jdbcSharedConfig;
    }

    public void setJdbcSharedConfig(String jdbcSharedConfig) {
        this.jdbcSharedConfig = jdbcSharedConfig;
    }

    public Map<String, String> getPrepared_Param_DataType() {
        return prepared_Param_DataType;
    }

    public void setPrepared_Param_DataType(Map<String, String> prepared_Param_DataType) {
        this.prepared_Param_DataType = prepared_Param_DataType;
    }

    public List<ClassParameter> getConfigAttributes(JdbcUpdateActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param1=new ClassParameter();
        param1.setName("timeOut");
        param1.setDefaultValue(activity.getTimeOut());

        ClassParameter param2=new ClassParameter();
        param2.setName("commit");
        param2.setDefaultValue(String.valueOf(activity.isCommit()));

        ClassParameter param3=new ClassParameter();
        param3.setName("emptyStringAsNull");
        param3.setDefaultValue(String.valueOf(activity.isEmptyStringAsNull()));

        ClassParameter param4=new ClassParameter();
        param4.setName("jdbcSharedConfig");
        param4.setDefaultValue(String.valueOf(activity.getJdbcSharedConfig()));

        ClassParameter param5=new ClassParameter();
        param5.setName("statement");
        param5.setDefaultValue(String.valueOf(activity.getStatement()));

        //fetch Prepared Param datatypes
        ClassParameter param6=new ClassParameter();
        param6.setName("prepared_Param_DataType");
        List<ClassParameter> listDataTypes = new ArrayList<ClassParameter>();
        HashMap<String,String> dataTypeParameters= (HashMap<String, String>) activity.getPrepared_Param_DataType();
        for(String key : dataTypeParameters.keySet())
        {
            ClassParameter classParam=new ClassParameter();
            classParam.setName(key);
            classParam.setType(dataTypeParameters.get(key));

            listDataTypes.add(classParam);
        }

        param6.setDefaultValue("empty");
        param6.setChildProperties(listDataTypes);

        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(param4);
        listParameters.add(param5);
        listParameters.add(param6);

        return listParameters;
    }

}
