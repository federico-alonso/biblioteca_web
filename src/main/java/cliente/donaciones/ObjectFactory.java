
package cliente.donaciones;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.donaciones package. 
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

    private final static QName _Exception_QNAME = new QName("http://publicadores/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.donaciones
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
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
     * Create an instance of {@link DtMaterialArray }
     * 
     */
    public DtMaterialArray createDtMaterialArray() {
        return new DtMaterialArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicadores/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
