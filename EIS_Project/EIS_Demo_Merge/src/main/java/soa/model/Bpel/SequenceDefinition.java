package soa.model.Bpel;

import java.util.ArrayList;
import java.util.List;

public class SequenceDefinition {

    //addded @Manoj
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<AbstractActivity> activities = new ArrayList<AbstractActivity>();

    public List<AbstractActivity> getActivities() {
        return activities;
    }

    public AssignDefinition getLastAssignActivity() {
        return (AssignDefinition) getActivities().get(getActivities().size() - 1);
    }

}
