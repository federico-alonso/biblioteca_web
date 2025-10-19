
package cliente.altaDonacionLibro;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtArticuloEspecial complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad descripcion.
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
     * Define el valor de la propiedad descripcion.
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
     * Obtiene el valor de la propiedad pesoKg.
     * 
     */
    public float getPesoKg() {
        return pesoKg;
    }

    /**
     * Define el valor de la propiedad pesoKg.
     * 
     */
    public void setPesoKg(float value) {
        this.pesoKg = value;
    }

    /**
     * Obtiene el valor de la propiedad dimensiones.
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
     * Define el valor de la propiedad dimensiones.
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
