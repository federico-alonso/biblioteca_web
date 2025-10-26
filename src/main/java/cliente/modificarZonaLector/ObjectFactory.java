
package cliente.modificarZonaLector;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.modificarZonaLector package. 
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

    private final static QName _LectorNoExisteExcepcion_QNAME = new QName("http://publicadores/", "LectorNoExisteExcepcion");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.modificarZonaLector
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link LectorNoExisteExcepcion }
     * 
     */
    public LectorNoExisteExcepcion createLectorNoExisteExcepcion() {
        return new LectorNoExisteExcepcion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LectorNoExisteExcepcion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://publicadores/", name = "LectorNoExisteExcepcion")
    public JAXBElement<LectorNoExisteExcepcion> createLectorNoExisteExcepcion(LectorNoExisteExcepcion value) {
        return new JAXBElement<LectorNoExisteExcepcion>(_LectorNoExisteExcepcion_QNAME, LectorNoExisteExcepcion.class, null, value);
    }

}
