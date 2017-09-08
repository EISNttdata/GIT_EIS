
package com.dell.dims.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcQueryActivity  extends Activity
{
    public JdbcQueryActivity(String name, ActivityType type) throws Exception {
        super(name, type);
    }

    public JdbcQueryActivity() throws Exception {
    }

    //private int timeOut;
    private String timeOut;
    private boolean commit;
    private int maxRows;

    private boolean emptyStringAsNull;
    private String jdbcSharedConfig;
    private String queryStatement;
    /**
     * Gets or sets the query statement parameters.
     * Key is the parameter Name
     * Value is the parameter Type (VARCHAR, INT, ...)
     * The query statement parameters.
     */
    private Map<String, String> queryStatementParameters;

    /**
     *Gets or sets the query queryOutputStatementParameters.
     * Name is SchemaColumn Name
     * Type is Schema Data Types
     *   SpecialOption is SchemaStatus
     */
    private List<ClassParameter> queryOutputStatementParameters;
    private String className;

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

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public boolean isEmptyStringAsNull() {
        return emptyStringAsNull;
    }

    public void setEmptyStringAsNull(boolean emptyStringAsNull) {
        this.emptyStringAsNull = emptyStringAsNull;
    }

    public String getJdbcSharedConfig() {
        return jdbcSharedConfig;
    }

    public void setJdbcSharedConfig(String jdbcSharedConfig) {
        this.jdbcSharedConfig = jdbcSharedConfig;
    }

    public String getQueryStatement() {
        return queryStatement;
    }

    public void setQueryStatement(String queryStatement) {
        this.queryStatement = queryStatement;
    }

    public Map<String, String> getQueryStatementParameters() {
        return queryStatementParameters;
    }

    public void setQueryStatementParameters(Map<String, String> queryStatementParameters) {
        this.queryStatementParameters = queryStatementParameters;
    }

    public List<ClassParameter> getQueryOutputStatementParameters() {
        return queryOutputStatementParameters;
    }

    public void setQueryOutputStatementParameters(List<ClassParameter> queryOutputStatementParameters) {
        this.queryOutputStatementParameters = queryOutputStatementParameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ClassParameter> getConfigAttributes(JdbcQueryActivity activity) throws Exception
    {
        List<ClassParameter> listParameters=new ArrayList<ClassParameter>();
        ClassParameter param=new ClassParameter();
        param.setName("timeOut");
        param.setDefaultValue(activity.getTimeOut());

        ClassParameter param1=new ClassParameter();
        param1.setName("commit");
        param1.setDefaultValue(String.valueOf(activity.isCommit()));

        ClassParameter param2=new ClassParameter();
        param2.setName("maxRows");
        param2.setDefaultValue(String.valueOf(activity.getMaxRows()));

        ClassParameter param3=new ClassParameter();
        param3.setName("emptyStringAsNull");
        param3.setDefaultValue(String.valueOf(activity.isEmptyStringAsNull()));

        ClassParameter param4=new ClassParameter();
        param4.setName("jdbcSharedConfig");
        param4.setDefaultValue(String.valueOf(activity.getJdbcSharedConfig()));

        ClassParameter param5=new ClassParameter();
        param5.setName("queryStatement");
        param5.setDefaultValue(String.valueOf(activity.getQueryStatement()));

        listParameters.add(param);
        listParameters.add(param1);
        listParameters.add(param2);
        listParameters.add(param3);
        listParameters.add(param4);
        listParameters.add(param5);

        return listParameters;
    }

}
