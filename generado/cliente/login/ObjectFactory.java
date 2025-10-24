
package cliente.login;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cliente.login package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cliente.login
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DtBibliotecario }
     * 
     */
    public DtBibliotecario createDtBibliotecario() {
        return new DtBibliotecario();
    }

    /**
     * Create an instance of {@link DtLoginResultado }
     * 
     */
    public DtLoginResultado createDtLoginResultado() {
        return new DtLoginResultado();
    }

    /**
     * Create an instance of {@link DtLector }
     * 
     */
    public DtLector createDtLector() {
        return new DtLector();
    }

}
