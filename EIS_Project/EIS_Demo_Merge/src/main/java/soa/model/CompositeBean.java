package soa.model;

import com.dell.dims.Model.bpel.Import;

import java.util.List;

/**
 * Created @MM 20/11/2017
 */
public class CompositeBean
{
  String name;
  String label;
  String compositeId;
  List<Import> imports;
  List<Component> components;
  List<Wire> wires;

  public CompositeBean(String name) {
    this.name = name;
  }

  public String getCompositeId() {
    return compositeId;
  }

  public void setCompositeId(String compositeId) {
    this.compositeId = compositeId;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<Import> getImports() {
    return imports;
  }

  public void setImports(List<Import> imports) {
    this.imports = imports;
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

  public List<Wire> getWires() {
    return wires;
  }

  public void setWires(List<Wire> wires) {
    this.wires = wires;
  }
}
