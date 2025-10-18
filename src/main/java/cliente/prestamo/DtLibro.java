
package cliente.prestamo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtLibro complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtLibro"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://publicadores/}dtMaterial"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cantidadPag" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtLibro", propOrder = {
    "titulo",
    "cantidadPag"
})
public class DtLibro
    extends DtMaterial
{

    protected String titulo;
    protected int cantidadPag;

    /**
     * Gets the value of the titulo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Sets the value of the titulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Gets the value of the cantidadPag property.
     * 
     */
    public int getCantidadPag() {
        return cantidadPag;
    }

    /**
     * Sets the value of the cantidadPag property.
     * 
     */
    public void setCantidadPag(int value) {
        this.cantidadPag = value;
    }

}
