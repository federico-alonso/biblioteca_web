
package cliente.login;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoLector.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoLector">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACTIVO"/>
 *     &lt;enumeration value="SUSPENDIDO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "estadoLector")
@XmlEnum
public enum EstadoLector {

    ACTIVO,
    SUSPENDIDO;

    public String value() {
        return name();
    }

    public static EstadoLector fromValue(String v) {
        return valueOf(v);
    }

}
