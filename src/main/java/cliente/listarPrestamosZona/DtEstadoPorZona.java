
package cliente.listarPrestamosZona;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtEstadoPorZona complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtEstadoPorZona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resumen" type="{http://publicadores/}resumenEstado" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="zona" type="{http://publicadores/}zona" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtEstadoPorZona", propOrder = {
    "resumen",
    "zona"
})
public class DtEstadoPorZona {

    @XmlElement(nillable = true)
    protected List<ResumenEstado> resumen;
    @XmlSchemaType(name = "string")
    protected Zona zona;

    /**
     * Gets the value of the resumen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resumen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResumen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResumenEstado }
     * 
     * 
     */
    public List<ResumenEstado> getResumen() {
        if (resumen == null) {
            resumen = new ArrayList<ResumenEstado>();
        }
        return this.resumen;
    }

    /**
     * Obtiene el valor de la propiedad zona.
     * 
     * @return
     *     possible object is
     *     {@link Zona }
     *     
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Define el valor de la propiedad zona.
     * 
     * @param value
     *     allowed object is
     *     {@link Zona }
     *     
     */
    public void setZona(Zona value) {
        this.zona = value;
    }

}
