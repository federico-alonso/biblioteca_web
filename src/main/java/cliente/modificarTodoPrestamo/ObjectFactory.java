
package cliente.modificarTodoPrestamo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.modificarTodoPrestamo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PrestamoNoExisteExcepcion_QNAME = new QName("http://publicadores/", "PrestamoNoExisteExcepcion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.modificarTodoPrestamo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrestamoNoExisteExcepcion }
     * 
     */
    public PrestamoNoExisteExcepcion createPrestamoNoExisteExcepcion() {
        return new PrestamoNoExisteExcepcion();
    }

    /**
     * Create an instance of {@link DtBibliotecario }
     * 
     */
    public DtBibliotecario createDtBibliotecario() {
        return new DtBibliotecario();
    }

    /**
     * Create an instance of {@link DtPrestamoArray }
     * 
     */
    public DtPrestamoArray createDtPrestamoArray() {
        return new DtPrestamoArray();
    }

    /**
     * Create an instance of {@link DtLector }
     * 
     */
    public DtLector createDtLector() {
        return new DtLector();
    }

    /**
     * Create an instance of {@link DtMaterialArray }
     * 
     */
    public DtMaterialArray createDtMaterialArray() {
        return new DtMaterialArray();
    }

    /**
     * Create an instance of {@link DtLectorArray }
     * 
     */
    public DtLectorArray createDtLectorArray() {
        return new DtLectorArray();
    }

    /**
     * Create an instance of {@link DtBibliotecarioArray }
     * 
     */
    public DtBibliotecarioArray createDtBibliotecarioArray() {
        return new DtBibliotecarioArray();
    }

    /**
     * Create an instance of {@link DtLibro }
     * 
     */
    public DtLibro createDtLibro() {
        return new DtLibro();
    }

    /**
     * Create an instance of {@link DtPrestamo }
     * 
     */
    public DtPrestamo createDtPrestamo() {
        return new DtPrestamo();
    }

    /**
     * Create an instance of {@link DtArticuloEspecial }
     * 
     */
    public DtArticuloEspecial createDtArticuloEspecial() {
        return new DtArticuloEspecial();
    }

    /**
     * Create an instance of {@link DtMaterial }
     * 
     */
    public DtMaterial createDtMaterial() {
        return new DtMaterial();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrestamoNoExisteExcepcion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://publicadores/", name = "PrestamoNoExisteExcepcion")
    public JAXBElement<PrestamoNoExisteExcepcion> createPrestamoNoExisteExcepcion(PrestamoNoExisteExcepcion value) {
        return new JAXBElement<PrestamoNoExisteExcepcion>(_PrestamoNoExisteExcepcion_QNAME, PrestamoNoExisteExcepcion.class, null, value);
    }

}
