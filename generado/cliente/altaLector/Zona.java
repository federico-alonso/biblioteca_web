
package cliente.altaLector;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para zona.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="zona">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BIBLIOTECA_CENTRAL"/>
 *     &lt;enumeration value="SUCURSAL_ESTE"/>
 *     &lt;enumeration value="SUCURSAL_OESTE"/>
 *     &lt;enumeration value="BIBLIOTECA_INFANTIL"/>
 *     &lt;enumeration value="ARCHIVO_GENERAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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
