
package cliente.prestamo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoPmo.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoPmo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENDIENTE"/>
 *     &lt;enumeration value="ACTIVO"/>
 *     &lt;enumeration value="DEVUELTO"/>
 *     &lt;enumeration value="EN_CURSO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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
