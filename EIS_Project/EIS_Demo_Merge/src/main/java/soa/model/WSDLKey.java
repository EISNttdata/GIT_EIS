package soa.model;

public class WSDLKey
{
  protected String targetNameSpace;
  protected String location;
  public WSDLKey(String targetNameSpace, String location)
  {
    this.targetNameSpace = targetNameSpace;
    this.location = location;
  }

  public String getLocation()
  {
    return this.location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getTargetNameSpace()
  {
    return this.targetNameSpace;
  }

  public void setTargetNameSpace(String targetNameSpace)
  {
    this.targetNameSpace = targetNameSpace;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    WSDLKey other = (WSDLKey)obj;
    if (this.targetNameSpace == null ? other.targetNameSpace != null : !this.targetNameSpace.equals(other.targetNameSpace)) {
      return false;
    }
      return this.location == null ? other.location == null : this.location.equals(other.location);
  }
  
  public int hashCode()
  {
    int hash = 3;
    hash = 67 * hash + (this.targetNameSpace != null ? this.targetNameSpace.hashCode() : 0);
    hash = 67 * hash + (this.location != null ? this.location.hashCode() : 0);
    return hash;
  }
}
