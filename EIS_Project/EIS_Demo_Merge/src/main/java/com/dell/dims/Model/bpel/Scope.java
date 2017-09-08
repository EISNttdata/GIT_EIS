//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.*;


/**
 *
 * 				There is no schema-level default for "exitOnStandardFault"
 * 				at "scope". Because, it will inherit default from enclosing scope
 * 				or process.
 *
 *
 * <p>Clase Java para tScope complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tScope">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}partnerLinks" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}messageExchanges" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}variables" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}correlationSets" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}faultHandlers" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}compensationHandler" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}terminationHandler" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}eventHandlers" minOccurs="0"/>
 *         &lt;group ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}activity"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isolated" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" default="no" />
 *       &lt;attribute name="exitOnStandardFault" type="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tBoolean" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tScope", propOrder = {
    "partnerLinks",
    "messageExchanges",
    "variables",
    "correlationSets",
    "faultHandlers",
    "compensationHandler",
    "terminationHandler",
    "eventHandlers",
    "assign",
    "compensate",
    "compensateScope",
    "empty",
    "exit",
    "extensionActivity",
    "flow",
    "forEach",
    "_if",
    "invoke",
    "pick",
    "receive",
    "repeatUntil",
    "reply",
    "rethrow",
    "scope",
    "sequence",
    "_throw",
    "validate",
    "wait",
    "_while"
})
public class Scope
  //  extends Activity
    extends StructuredActivity
{

    protected PartnerLinks partnerLinks;
    protected MessageExchanges messageExchanges;
    protected Variables variables;
    protected CorrelationSets correlationSets;
    protected FaultHandlers faultHandlers;
    protected ActivityContainer compensationHandler;
    protected ActivityContainer terminationHandler;
    protected EventHandlers eventHandlers;
    protected Assign assign;
    protected Compensate compensate;
    protected CompensateScope compensateScope;
    protected Empty empty;
    protected Exit exit;
    protected ExtensionActivity extensionActivity;
    protected Flow flow;
    protected ForEach forEach;
    @XmlElement(name = "if")
    protected If _if;
    protected Invoke invoke;
    protected Pick pick;
    protected Receive receive;
    protected RepeatUntil repeatUntil;
    protected Reply reply;
    protected Rethrow rethrow;
    protected Scope scope;
    protected Sequence sequence;
    @XmlElement(name = "throw")
    protected Throw _throw;
    protected Validate validate;
    protected Wait wait;
    @XmlElement(name = "while")
    protected While _while;
    @XmlAttribute(name = "isolated")
    protected Boolean isolated;
    @XmlAttribute(name = "exitOnStandardFault")
    protected Boolean exitOnStandardFault;

  public Scope()
  {

  }

  public Scope(String name)
  {
    this.name=name;
  }

    /**
     * Obtiene el valor de la propiedad partnerLinks.
     *
     * @return
     *     possible object is
     *     {@link PartnerLinks }
     *
     */
    public PartnerLinks getPartnerLinks() {
        return partnerLinks;
    }

    /**
     * Define el valor de la propiedad partnerLinks.
     *
     * @param value
     *     allowed object is
     *     {@link PartnerLinks }
     *
     */
    public void setPartnerLinks(PartnerLinks value) {
        this.partnerLinks = value;
    }

    /**
     * Obtiene el valor de la propiedad messageExchanges.
     *
     * @return
     *     possible object is
     *     {@link MessageExchanges }
     *
     */
    public MessageExchanges getMessageExchanges() {
        return messageExchanges;
    }

    /**
     * Define el valor de la propiedad messageExchanges.
     *
     * @param value
     *     allowed object is
     *     {@link MessageExchanges }
     *
     */
    public void setMessageExchanges(MessageExchanges value) {
        this.messageExchanges = value;
    }

    /**
     * Obtiene el valor de la propiedad variables.
     *
     * @return
     *     possible object is
     *     {@link Variables }
     *
     */
    public Variables getVariables() {
        return variables;
    }

    /**
     * Define el valor de la propiedad variables.
     *
     * @param value
     *     allowed object is
     *     {@link Variables }
     *
     */
    public void setVariables(Variables value) {
        this.variables = value;
    }

    /**
     * Obtiene el valor de la propiedad correlationSets.
     *
     * @return
     *     possible object is
     *     {@link CorrelationSets }
     *
     */
    public CorrelationSets getCorrelationSets() {
        return correlationSets;
    }

    /**
     * Define el valor de la propiedad correlationSets.
     *
     * @param value
     *     allowed object is
     *     {@link CorrelationSets }
     *
     */
    public void setCorrelationSets(CorrelationSets value) {
        this.correlationSets = value;
    }

    /**
     * Obtiene el valor de la propiedad faultHandlers.
     *
     * @return
     *     possible object is
     *     {@link FaultHandlers }
     *
     */
    public FaultHandlers getFaultHandlers() {
        return faultHandlers;
    }

    /**
     * Define el valor de la propiedad faultHandlers.
     *
     * @param value
     *     allowed object is
     *     {@link FaultHandlers }
     *
     */
    public void setFaultHandlers(FaultHandlers value) {
        this.faultHandlers = value;
    }

    /**
     * Obtiene el valor de la propiedad compensationHandler.
     *
     * @return
     *     possible object is
     *     {@link ActivityContainer }
     *
     */
    public ActivityContainer getCompensationHandler() {
        return compensationHandler;
    }

    /**
     * Define el valor de la propiedad compensationHandler.
     *
     * @param value
     *     allowed object is
     *     {@link ActivityContainer }
     *
     */
    public void setCompensationHandler(ActivityContainer value) {
        this.compensationHandler = value;
    }

    /**
     * Obtiene el valor de la propiedad terminationHandler.
     *
     * @return
     *     possible object is
     *     {@link ActivityContainer }
     *
     */
    public ActivityContainer getTerminationHandler() {
        return terminationHandler;
    }

    /**
     * Define el valor de la propiedad terminationHandler.
     *
     * @param value
     *     allowed object is
     *     {@link ActivityContainer }
     *
     */
    public void setTerminationHandler(ActivityContainer value) {
        this.terminationHandler = value;
    }

    /**
     * Obtiene el valor de la propiedad eventHandlers.
     *
     * @return
     *     possible object is
     *     {@link EventHandlers }
     *
     */
    public EventHandlers getEventHandlers() {
        return eventHandlers;
    }

    /**
     * Define el valor de la propiedad eventHandlers.
     *
     * @param value
     *     allowed object is
     *     {@link EventHandlers }
     *
     */
    public void setEventHandlers(EventHandlers value) {
        this.eventHandlers = value;
    }

    /**
     * Obtiene el valor de la propiedad assign.
     *
     * @return
     *     possible object is
     *     {@link Assign }
     *
     */
    public Assign getAssign() {
        return assign;
    }

    /**
     * Define el valor de la propiedad assign.
     *
     * @param value
     *     allowed object is
     *     {@link Assign }
     *
     */
    public void setAssign(Assign value) {
        this.assign = value;
    }

    /**
     * Obtiene el valor de la propiedad compensate.
     *
     * @return
     *     possible object is
     *     {@link Compensate }
     *
     */
    public Compensate getCompensate() {
        return compensate;
    }

    /**
     * Define el valor de la propiedad compensate.
     *
     * @param value
     *     allowed object is
     *     {@link Compensate }
     *
     */
    public void setCompensate(Compensate value) {
        this.compensate = value;
    }

    /**
     * Obtiene el valor de la propiedad compensateScope.
     *
     * @return
     *     possible object is
     *     {@link CompensateScope }
     *
     */
    public CompensateScope getCompensateScope() {
        return compensateScope;
    }

    /**
     * Define el valor de la propiedad compensateScope.
     *
     * @param value
     *     allowed object is
     *     {@link CompensateScope }
     *
     */
    public void setCompensateScope(CompensateScope value) {
        this.compensateScope = value;
    }

    /**
     * Obtiene el valor de la propiedad empty.
     *
     * @return
     *     possible object is
     *     {@link Empty }
     *
     */
    public Empty getEmpty() {
        return empty;
    }

    /**
     * Define el valor de la propiedad empty.
     *
     * @param value
     *     allowed object is
     *     {@link Empty }
     *
     */
    public void setEmpty(Empty value) {
        this.empty = value;
    }

    /**
     * Obtiene el valor de la propiedad exit.
     *
     * @return
     *     possible object is
     *     {@link Exit }
     *
     */
    public Exit getExit() {
        return exit;
    }

    /**
     * Define el valor de la propiedad exit.
     *
     * @param value
     *     allowed object is
     *     {@link Exit }
     *
     */
    public void setExit(Exit value) {
        this.exit = value;
    }

    /**
     * Obtiene el valor de la propiedad extensionActivity.
     *
     * @return
     *     possible object is
     *     {@link ExtensionActivity }
     *
     */
    public ExtensionActivity getExtensionActivity() {
        return extensionActivity;
    }

    /**
     * Define el valor de la propiedad extensionActivity.
     *
     * @param value
     *     allowed object is
     *     {@link ExtensionActivity }
     *
     */
    public void setExtensionActivity(ExtensionActivity value) {
        this.extensionActivity = value;
    }

    /**
     * Obtiene el valor de la propiedad flow.
     *
     * @return
     *     possible object is
     *     {@link Flow }
     *
     */
    public Flow getFlow() {
        return flow;
    }

    /**
     * Define el valor de la propiedad flow.
     *
     * @param value
     *     allowed object is
     *     {@link Flow }
     *
     */
    public void setFlow(Flow value) {
        this.flow = value;
    }

    /**
     * Obtiene el valor de la propiedad forEach.
     *
     * @return
     *     possible object is
     *     {@link ForEach }
     *
     */
    public ForEach getForEach() {
        return forEach;
    }

    /**
     * Define el valor de la propiedad forEach.
     *
     * @param value
     *     allowed object is
     *     {@link ForEach }
     *
     */
    public void setForEach(ForEach value) {
        this.forEach = value;
    }

    /**
     * Obtiene el valor de la propiedad if.
     *
     * @return
     *     possible object is
     *     {@link If }
     *
     */
    public If getIf() {
        return _if;
    }

    /**
     * Define el valor de la propiedad if.
     *
     * @param value
     *     allowed object is
     *     {@link If }
     *
     */
    public void setIf(If value) {
        this._if = value;
    }

    /**
     * Obtiene el valor de la propiedad invoke.
     *
     * @return
     *     possible object is
     *     {@link Invoke }
     *
     */
    public Invoke getInvoke() {
        return invoke;
    }

    /**
     * Define el valor de la propiedad invoke.
     *
     * @param value
     *     allowed object is
     *     {@link Invoke }
     *
     */
    public void setInvoke(Invoke value) {
        this.invoke = value;
    }

    /**
     * Obtiene el valor de la propiedad pick.
     *
     * @return
     *     possible object is
     *     {@link Pick }
     *
     */
    public Pick getPick() {
        return pick;
    }

    /**
     * Define el valor de la propiedad pick.
     *
     * @param value
     *     allowed object is
     *     {@link Pick }
     *
     */
    public void setPick(Pick value) {
        this.pick = value;
    }

    /**
     * Obtiene el valor de la propiedad receive.
     *
     * @return
     *     possible object is
     *     {@link Receive }
     *
     */
    public Receive getReceive() {
        return receive;
    }

    /**
     * Define el valor de la propiedad receive.
     *
     * @param value
     *     allowed object is
     *     {@link Receive }
     *
     */
    public void setReceive(Receive value) {
        this.receive = value;
    }

    /**
     * Obtiene el valor de la propiedad repeatUntil.
     *
     * @return
     *     possible object is
     *     {@link RepeatUntil }
     *
     */
    public RepeatUntil getRepeatUntil() {
        return repeatUntil;
    }

    /**
     * Define el valor de la propiedad repeatUntil.
     *
     * @param value
     *     allowed object is
     *     {@link RepeatUntil }
     *
     */
    public void setRepeatUntil(RepeatUntil value) {
        this.repeatUntil = value;
    }

    /**
     * Obtiene el valor de la propiedad reply.
     *
     * @return
     *     possible object is
     *     {@link Reply }
     *
     */
    public Reply getReply() {
        return reply;
    }

    /**
     * Define el valor de la propiedad reply.
     *
     * @param value
     *     allowed object is
     *     {@link Reply }
     *
     */
    public void setReply(Reply value) {
        this.reply = value;
    }

    /**
     * Obtiene el valor de la propiedad rethrow.
     *
     * @return
     *     possible object is
     *     {@link Rethrow }
     *
     */
    public Rethrow getRethrow() {
        return rethrow;
    }

    /**
     * Define el valor de la propiedad rethrow.
     *
     * @param value
     *     allowed object is
     *     {@link Rethrow }
     *
     */
    public void setRethrow(Rethrow value) {
        this.rethrow = value;
    }

    /**
     * Obtiene el valor de la propiedad scope.
     *
     * @return
     *     possible object is
     *     {@link Scope }
     *
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Define el valor de la propiedad scope.
     *
     * @param value
     *     allowed object is
     *     {@link Scope }
     *
     */
    public void setScope(Scope value) {
        this.scope = value;
    }

    /**
     * Obtiene el valor de la propiedad sequence.
     *
     * @return
     *     possible object is
     *     {@link Sequence }
     *
     */
    public Sequence getSequence() {
        return sequence;
    }

    /**
     * Define el valor de la propiedad sequence.
     *
     * @param value
     *     allowed object is
     *     {@link Sequence }
     *
     */
    public void setSequence(Sequence value) {
        this.sequence = value;
    }

    /**
     * Obtiene el valor de la propiedad throw.
     *
     * @return
     *     possible object is
     *     {@link Throw }
     *
     */
    public Throw getThrow() {
        return _throw;
    }

    /**
     * Define el valor de la propiedad throw.
     *
     * @param value
     *     allowed object is
     *     {@link Throw }
     *
     */
    public void setThrow(Throw value) {
        this._throw = value;
    }

    /**
     * Obtiene el valor de la propiedad validate.
     *
     * @return
     *     possible object is
     *     {@link Validate }
     *
     */
    public Validate getValidate() {
        return validate;
    }

    /**
     * Define el valor de la propiedad validate.
     *
     * @param value
     *     allowed object is
     *     {@link Validate }
     *
     */
    public void setValidate(Validate value) {
        this.validate = value;
    }

    /**
     * Obtiene el valor de la propiedad wait.
     *
     * @return
     *     possible object is
     *     {@link Wait }
     *
     */
    public Wait getWait() {
        return wait;
    }

    /**
     * Define el valor de la propiedad wait.
     *
     * @param value
     *     allowed object is
     *     {@link Wait }
     *
     */
    public void setWait(Wait value) {
        this.wait = value;
    }

    /**
     * Obtiene el valor de la propiedad while.
     *
     * @return
     *     possible object is
     *     {@link While }
     *
     */
    public While getWhile() {
        return _while;
    }

    /**
     * Define el valor de la propiedad while.
     *
     * @param value
     *     allowed object is
     *     {@link While }
     *
     */
    public void setWhile(While value) {
        this._while = value;
    }

    /**
     * Obtiene el valor de la propiedad isolated.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getIsolated() {
        if (isolated == null) {
            return Boolean.NO;
        } else {
            return isolated;
        }
    }

    /**
     * Define el valor de la propiedad isolated.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsolated(Boolean value) {
        this.isolated = value;
    }

    /**
     * Obtiene el valor de la propiedad exitOnStandardFault.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean getExitOnStandardFault() {
        return exitOnStandardFault;
    }

    /**
     * Define el valor de la propiedad exitOnStandardFault.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setExitOnStandardFault(Boolean value) {
        this.exitOnStandardFault = value;
    }

}
