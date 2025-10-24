
package cliente.prestamo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtMaterialConPrestamo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtMaterialConPrestamo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="material" type="{http://publicadores/}dtMaterial" minOccurs="0"/>
 *         &lt;element name="prestamo" type="{http://publicadores/}dtPrestamo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
     * Obtiene el valor de la propiedad material.
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
     * Define el valor de la propiedad material.
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
     * Obtiene el valor de la propiedad prestamo.
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
     * Define el valor de la propiedad prestamo.
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
