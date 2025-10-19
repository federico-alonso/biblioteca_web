
package cliente.altaDonacionEspecial;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtLibro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad titulo.
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
     * Define el valor de la propiedad titulo.
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
     * Obtiene el valor de la propiedad cantidadPag.
     * 
     */
    public int getCantidadPag() {
        return cantidadPag;
    }

    /**
     * Define el valor de la propiedad cantidadPag.
     * 
     */
    public void setCantidadPag(int value) {
        this.cantidadPag = value;
    }

}
