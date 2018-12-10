package dtos;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "usuario": string,
 *      "clave": string
 *   }
 * </pre> <br>
 *
 * <pre>
 *
 *   {
 *      "usuario": "AndresVM",
 *      "Clave" "1234"
 *   }
 *
 * </pre>
 *
 */
public class UsuarioDTO implements Serializable {

    private String usuario;

    private String clave;

    /**
     * Constructor por defecto
     */
    public UsuarioDTO() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    

}
