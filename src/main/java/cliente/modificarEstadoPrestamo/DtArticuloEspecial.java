
package cliente.modificarEstadoPrestamo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtArticuloEspecial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtArticuloEspecial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://publicadores/}dtMaterial"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pesoKg" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="dimensiones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtArticuloEspecial", propOrder = {
    "descripcion",
    "pesoKg",
    "dimensiones"
})
public class DtArticuloEspecial
    extends DtMaterial
{

    protected String descripcion;
    protected float pesoKg;
    protected String dimensiones;

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the pesoKg property.
     * 
     */
    public float getPesoKg() {
        return pesoKg;
    }

    /**
     * Sets the value of the pesoKg property.
     * 
     */
    public void setPesoKg(float value) {
        this.pesoKg = value;
    }

    /**
     * Gets the value of the dimensiones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDimensiones() {
        return dimensiones;
    }

    /**
     * Sets the value of the dimensiones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDimensiones(String value) {
        this.dimensiones = value;
    }

}
