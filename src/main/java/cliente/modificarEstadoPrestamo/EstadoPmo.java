
package cliente.modificarEstadoPrestamo;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estadoPmo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="estadoPmo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PENDIENTE"/&gt;
 *     &lt;enumeration value="ACTIVO"/&gt;
 *     &lt;enumeration value="DEVUELTO"/&gt;
 *     &lt;enumeration value="EN_CURSO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "estadoPmo")
@XmlEnum
public enum EstadoPmo {

    PENDIENTE,
    ACTIVO,
    DEVUELTO,
    EN_CURSO;

    public String value() {
        return name();
    }

    public static EstadoPmo fromValue(String v) {
        return valueOf(v);
    }

}
