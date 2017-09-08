package soa.model;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Manoj_Mehta on 4/12/2017.
 */
public class Component
{
    private String name;
    private String implementationBpel;
    private List<Service> listService;
    private List<Reference> listReference;
    ComponentType componentType;

    private LinkedHashSet<String> componentNames;

    public LinkedHashSet<String> getComponentNames() {
        return componentNames;
    }

    public void setComponentNames(LinkedHashSet<String> componentNames) {
        this.componentNames = componentNames;
    }

    public List<Service> getListService() {
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
    }

    public List<Reference> getListReference() {
        return listReference;
    }

    public void setListReference(List<Reference> listReference) {
        this.listReference = listReference;
    }

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImplementationBpel() {
        return implementationBpel;
    }

    public void setImplementationBpel(String implementationBpel) {
        this.implementationBpel = implementationBpel;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}
