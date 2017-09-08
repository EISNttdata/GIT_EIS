package soa.model.soap;

public abstract class ConcreteBinding
{
  Binding binding;
  public ConcreteBinding(Binding binding)
  {
    this.binding = binding;
  }
  
  public Binding getBinding() {
    return this.binding;
  }
}
