
package cliente.modificarEstadoPrestamo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dtBibliotecario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dtBibliotecario"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://publicadores/}dtUsuario"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtBibliotecario", propOrder = {
    "numero"
})
public class DtBibliotecario
    extends DtUsuario
{

    protected int numero;

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

}
