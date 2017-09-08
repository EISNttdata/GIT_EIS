package soa.output;




public class Wire
{
  Source source;
  

  Target target;
  


  public Wire(Source source, Target target)
  {
    this.source = source;
    this.target = target;
  }
  
  public Source getSource() {
    return this.source;
  }
  
  public Target getTarget() {
    return this.target;
  }
}
