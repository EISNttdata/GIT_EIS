//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.5-2
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: PM.02.21 a las 09:44:00 PM CET
//


package com.dell.dims.Model.bpel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para tIf complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="tIf">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}tActivity">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}condition"/>
 *         &lt;group ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}activity"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}elseif" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://docs.oasis-open.org/wsbpel/2.0/process/executable}else" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tIf", propOrder = {
    "condition",
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
    "_while",
    "elseif",
    "_else"
})
public class If
    extends Activity
  //  extends StructuredActivity

{

    @XmlElement(required = true)
    protected BooleanExpr condition;
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
    protected List<Elseif> elseif;
    @XmlElement(name = "else")
    protected ActivityContainer _else;

    /**
     * Obtiene el valor de la propiedad condition.
     *
     * @return
     *     possible object is
     *     {@link BooleanExpr }
     *
     */
    public BooleanExpr getCondition() {
        return condition;
    }

    /**
     * Define el valor de la propiedad condition.
     *
     * @param value
     *     allowed object is
     *     {@link BooleanExpr }
     *
     */
    public void setCondition(BooleanExpr value) {
        this.condition = value;
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
     * Gets the value of the elseif property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elseif property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElseif().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Elseif }
     *
     *
     */
    public List<Elseif> getElseif() {
        if (elseif == null) {
            elseif = new ArrayList<Elseif>();
        }
        return this.elseif;
    }

    /**
     * Obtiene el valor de la propiedad else.
     *
     * @return
     *     possible object is
     *     {@link ActivityContainer }
     *
     */
    public ActivityContainer getElse() {
        return _else;
    }

    /**
     * Define el valor de la propiedad else.
     *
     * @param value
     *     allowed object is
     *     {@link ActivityContainer }
     *
     */
    public void setElse(ActivityContainer value) {
        this._else = value;
    }

}
