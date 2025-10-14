
package cliente.altaLector;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.altaLector package. 
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

    private final static QName _LectorRepetidoExcepcion_QNAME = new QName("http://publicadores/", "LectorRepetidoExcepcion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.altaLector
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LectorRepetidoExcepcion }
     * 
     */
    public LectorRepetidoExcepcion createLectorRepetidoExcepcion() {
        return new LectorRepetidoExcepcion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LectorRepetidoExcepcion }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LectorRepetidoExcepcion }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicadores/", name = "LectorRepetidoExcepcion")
    public JAXBElement<LectorRepetidoExcepcion> createLectorRepetidoExcepcion(LectorRepetidoExcepcion value) {
        return new JAXBElement<LectorRepetidoExcepcion>(_LectorRepetidoExcepcion_QNAME, LectorRepetidoExcepcion.class, null, value);
    }

}
