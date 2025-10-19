
package cliente.modificarEstadoPrestamo;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtPrestamo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtPrestamo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="material" type="{http://publicadores/}dtMaterial" minOccurs="0"/&gt;
 *         &lt;element name="lector" type="{http://publicadores/}dtLector" minOccurs="0"/&gt;
 *         &lt;element name="bibliotecario" type="{http://publicadores/}dtBibliotecario" minOccurs="0"/&gt;
 *         &lt;element name="fechaSolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="fechaDevolucion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="estado" type="{http://publicadores/}estadoPmo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

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
     * Gets the value of the lector property.
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
     * Sets the value of the lector property.
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
     * Gets the value of the bibliotecario property.
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
     * Sets the value of the bibliotecario property.
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
     * Gets the value of the fechaSolicitud property.
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
     * Sets the value of the fechaSolicitud property.
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
     * Gets the value of the fechaDevolucion property.
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
     * Sets the value of the fechaDevolucion property.
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
     * Gets the value of the estado property.
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
     * Sets the value of the estado property.
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
