/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author arquisicion
 */
@Entity
public class UsuarioEntity implements Serializable {

    @Id
    private String usuario;

    private String hashClave;
    
    @Lob
    private String ultimoToken;

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
     * @return the hashClave
     */
    public String getHashClave() {
        return hashClave;
    }

    /**
     * @param hashClave the hashClave to set
     */
    public void setHashClave(String hashClave) {
        this.hashClave = hashClave;
    }

    /**
     * @return the ultimoToken
     */
    public String getUltimoToken() {
        return ultimoToken;
    }

    /**
     * @param ultimoToken the ultimoToken to set
     */
    public void setUltimoToken(String ultimoToken) {
        this.ultimoToken = ultimoToken;
    }

}
