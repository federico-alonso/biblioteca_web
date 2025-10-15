
package cliente.login;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for zona.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="zona"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BIBLIOTECA_CENTRAL"/&gt;
 *     &lt;enumeration value="SUCURSAL_ESTE"/&gt;
 *     &lt;enumeration value="SUCURSAL_OESTE"/&gt;
 *     &lt;enumeration value="BIBLIOTECA_INFANTIL"/&gt;
 *     &lt;enumeration value="ARCHIVO_GENERAL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "zona")
@XmlEnum
public enum Zona {

    BIBLIOTECA_CENTRAL,
    SUCURSAL_ESTE,
    SUCURSAL_OESTE,
    BIBLIOTECA_INFANTIL,
    ARCHIVO_GENERAL;

    public String value() {
        return name();
    }

    public static Zona fromValue(String v) {
        return valueOf(v);
    }

}
