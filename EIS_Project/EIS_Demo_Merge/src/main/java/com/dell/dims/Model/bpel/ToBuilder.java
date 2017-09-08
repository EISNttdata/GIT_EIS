package com.dell.dims.Model.bpel;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

public class ToBuilder {
  private List<Object> content;
  private String expressionLanguage;
  private String variable;
  private String part;
  private QName property;
  private String partnerLink;
  private Map<QName, String> otherAttributes;

  public ToBuilder setContent(List<Object> content) {
    this.content = content;
    return this;
  }

  public ToBuilder setExpressionLanguage(String expressionLanguage) {
    this.expressionLanguage = expressionLanguage;
    return this;
  }

  public ToBuilder setVariable(String variable) {
    this.variable = variable;
    return this;
  }

  public ToBuilder setPart(String part) {
    this.part = part;
    return this;
  }

  public ToBuilder setProperty(QName property) {
    this.property = property;
    return this;
  }

  public ToBuilder setPartnerLink(String partnerLink) {
    this.partnerLink = partnerLink;
    return this;
  }

  public ToBuilder setOtherAttributes(Map<QName, String> otherAttributes) {
    this.otherAttributes = otherAttributes;
    return this;
  }

  public To createTo() {
    return new To(content, expressionLanguage, variable, part, property, partnerLink, otherAttributes);
  }
}
