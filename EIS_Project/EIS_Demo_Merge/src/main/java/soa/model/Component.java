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
   /* private List<Service> listService;
    private List<Reference> listReference;*/
    ComponentType componentType;

    public String getImplementationBpel() {
        return implementationBpel;
    }

    public void setImplementationBpel(String implementationBpel) {
        this.implementationBpel = implementationBpel;
    }

    private LinkedHashSet<String> componentNames;

    public LinkedHashSet<String> getComponentNames() {
        return componentNames;
    }

    public void setComponentNames(LinkedHashSet<String> componentNames) {
        this.componentNames = componentNames;
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

   public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}
