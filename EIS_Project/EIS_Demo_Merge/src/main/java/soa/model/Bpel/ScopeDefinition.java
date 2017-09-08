package soa.model.Bpel;
import java.util.List;

/**
 * Created by Manoj_Mehta on 5/25/2017.
 */
public class ScopeDefinition {
    String name;
    List<VariableDefinition> variablesList;
    List<AssignDefinition> assignList;
    List<InvokeDefinition> invokeList;

    public List<InvokeDefinition> getInvokeList() {
        return invokeList;
    }

    public void setInvokeList(List<InvokeDefinition> invokeList) {
        this.invokeList = invokeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VariableDefinition> getVariablesList() {
        return variablesList;
    }

    public void setVariablesList(List<VariableDefinition> variablesList) {
        this.variablesList = variablesList;
    }

    public List<AssignDefinition> getAssignList() {
        return assignList;
    }

    public void setAssignList(List<AssignDefinition> assignList) {
        this.assignList = assignList;
    }
}


