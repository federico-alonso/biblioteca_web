
package cliente.prestamo;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.prestamo package. 
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

    private final static QName _PrestamoYaExisteExcepcion_QNAME = new QName("http://publicadores/", "PrestamoYaExisteExcepcion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.prestamo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrestamoYaExisteExcepcion }
     * 
     */
    public PrestamoYaExisteExcepcion createPrestamoYaExisteExcepcion() {
        return new PrestamoYaExisteExcepcion();
    }

    /**
     * Create an instance of {@link DtBibliotecario }
     * 
     */
    public DtBibliotecario createDtBibliotecario() {
        return new DtBibliotecario();
    }

    /**
     * Create an instance of {@link DtLector }
     * 
     */
    public DtLector createDtLector() {
        return new DtLector();
    }

    /**
     * Create an instance of {@link DtPrestamo }
     * 
     */
    public DtPrestamo createDtPrestamo() {
        return new DtPrestamo();
    }

    /**
     * Create an instance of {@link DtMaterial }
     * 
     */
    public DtMaterial createDtMaterial() {
        return new DtMaterial();
    }

    /**
     * Create an instance of {@link DtLibro }
     * 
     */
    public DtLibro createDtLibro() {
        return new DtLibro();
    }

    /**
     * Create an instance of {@link DtArticuloEspecial }
     * 
     */
    public DtArticuloEspecial createDtArticuloEspecial() {
        return new DtArticuloEspecial();
    }

    /**
     * Create an instance of {@link DtMaterialConPrestamo }
     * 
     */
    public DtMaterialConPrestamo createDtMaterialConPrestamo() {
        return new DtMaterialConPrestamo();
    }

    /**
     * Create an instance of {@link DtPrestamoSimple }
     * 
     */
    public DtPrestamoSimple createDtPrestamoSimple() {
        return new DtPrestamoSimple();
    }

    /**
     * Create an instance of {@link DtPrestamoArray }
     * 
     */
    public DtPrestamoArray createDtPrestamoArray() {
        return new DtPrestamoArray();
    }

    /**
     * Create an instance of {@link DtMaterialConPrestamoArray }
     * 
     */
    public DtMaterialConPrestamoArray createDtMaterialConPrestamoArray() {
        return new DtMaterialConPrestamoArray();
    }

    /**
     * Create an instance of {@link DtPrestamoSimpleArray }
     * 
     */
    public DtPrestamoSimpleArray createDtPrestamoSimpleArray() {
        return new DtPrestamoSimpleArray();
    }

    /**
     * Create an instance of {@link DtMaterialArray }
     * 
     */
    public DtMaterialArray createDtMaterialArray() {
        return new DtMaterialArray();
    }

    /**
     * Create an instance of {@link DtBibliotecarioArray }
     * 
     */
    public DtBibliotecarioArray createDtBibliotecarioArray() {
        return new DtBibliotecarioArray();
    }

    /**
     * Create an instance of {@link DtLectorArray }
     * 
     */
    public DtLectorArray createDtLectorArray() {
        return new DtLectorArray();
    }

    /**
     * Create an instance of {@link AnyTypeArray }
     * 
     */
    public AnyTypeArray createAnyTypeArray() {
        return new AnyTypeArray();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrestamoYaExisteExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PrestamoYaExisteExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicadores/", name = "PrestamoYaExisteExcepcion")
    public JAXBElement<PrestamoYaExisteExcepcion> createPrestamoYaExisteExcepcion(PrestamoYaExisteExcepcion value) {
        return new JAXBElement<PrestamoYaExisteExcepcion>(_PrestamoYaExisteExcepcion_QNAME, PrestamoYaExisteExcepcion.class, null, value);
    }

}
