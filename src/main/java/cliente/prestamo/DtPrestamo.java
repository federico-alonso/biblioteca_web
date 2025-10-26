
package cliente.prestamo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para dtPrestamo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtPrestamo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="material" type="{http://publicadores/}dtMaterial" minOccurs="0"/>
 *         &lt;element name="lector" type="{http://publicadores/}dtLector" minOccurs="0"/>
 *         &lt;element name="bibliotecario" type="{http://publicadores/}dtBibliotecario" minOccurs="0"/>
 *         &lt;element name="fechaSolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaDevolucion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://publicadores/}estadoPmo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPrestamo", propOrder = {
    "id",
    "material",
    "lector",
    "bibliotecario",
    "fechaSolicitud",
    "fechaDevolucion",
    "estado"
})
public class DtPrestamo {

    protected long id;
    protected DtMaterial material;
    protected DtLector lector;
    protected DtBibliotecario bibliotecario;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaSolicitud;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaDevolucion;
    @XmlSchemaType(name = "string")
    protected EstadoPmo estado;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

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
     * Obtiene el valor de la propiedad lector.
     * 
     * @return
     *     possible object is
     *     {@link DtLector }
     *     
     */
    public DtLector getLector() {
        return lector;
    }

    /**
     * Define el valor de la propiedad lector.
     * 
     * @param value
     *     allowed object is
     *     {@link DtLector }
     *     
     */
    public void setLector(DtLector value) {
        this.lector = value;
    }

    /**
     * Obtiene el valor de la propiedad bibliotecario.
     * 
     * @return
     *     possible object is
     *     {@link DtBibliotecario }
     *     
     */
    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    /**
     * Define el valor de la propiedad bibliotecario.
     * 
     * @param value
     *     allowed object is
     *     {@link DtBibliotecario }
     *     
     */
    public void setBibliotecario(DtBibliotecario value) {
        this.bibliotecario = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Define el valor de la propiedad fechaSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaSolicitud(XMLGregorianCalendar value) {
        this.fechaSolicitud = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaDevolucion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Define el valor de la propiedad fechaDevolucion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaDevolucion(XMLGregorianCalendar value) {
        this.fechaDevolucion = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoPmo }
     *     
     */
    public EstadoPmo getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoPmo }
     *     
     */
    public void setEstado(EstadoPmo value) {
        this.estado = value;
    }

}
