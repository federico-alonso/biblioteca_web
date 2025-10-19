
package cliente.prestamo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtMaterialConPrestamo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtMaterialConPrestamo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="material" type="{http://publicadores/}dtMaterial" minOccurs="0"/&gt;
 *         &lt;element name="prestamo" type="{http://publicadores/}dtPrestamo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtMaterialConPrestamo", propOrder = {
    "material",
    "prestamo"
})
public class DtMaterialConPrestamo {

    protected DtMaterial material;
    protected DtPrestamo prestamo;

    /**
     * Gets the value of the material property.
     * 
     * @return
     *     possible object is
     *     {@link DtMaterial }
     *     
     */
    public DtMaterial getMaterial() {
        return material;
    }

    /**
     * Sets the value of the material property.
     * 
     * @param value
     *     allowed object is
     *     {@link DtMaterial }
     *     
     */
    public void setMaterial(DtMaterial value) {
        this.material = value;
    }

    /**
     * Gets the value of the prestamo property.
     * 
     * @return
     *     possible object is
     *     {@link DtPrestamo }
     *     
     */
    public DtPrestamo getPrestamo() {
        return prestamo;
    }

    /**
     * Sets the value of the prestamo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DtPrestamo }
     *     
     */
    public void setPrestamo(DtPrestamo value) {
        this.prestamo = value;
    }

}
