package logic;

import entities.UsuarioEntity;
import exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import persistence.UsuarioPersistence;

/**
 *
 * @author arquisicion
 */
@Stateless
public class UsuarioLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());

    @Inject
    private UsuarioPersistence persistence;

    /**
     * Crea un usuario en la persistencia.
     *
     * @param usuarioEntity La entidad que representa el usuario a persistir.
     * @return La entiddad de el usuario luego de persistirla.
     * @throws exceptions.BusinessLogicException si ya existe un usuario con el
     * mismo usuario
     */
    public UsuarioEntity registrarUsuario(UsuarioEntity usuarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de el usuario");
        if (persistence.find(usuarioEntity.getUsuario()) != null) {
            throw new BusinessLogicException("Ya existe un usuario con el usuario \"" + usuarioEntity.getUsuario() + "\"");
        }
        persistence.create(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de el usuario");
        return usuarioEntity;
    }

    /**
     *
     * Obtener todas los usuarios existentes en la base de datos.
     *
     * @return una lista de usuarios.
     */
    public List<String> getUsuarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los usuarios");
        List<String> usuarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas los usuarios");
        return usuarios;
    }

    /**
     *
     * Obtener un usuario por medio de su usuario.
     *
     * @param usuariosUsuario: usuario de el usuario para ser buscada.
     * @return el usuario solicitado por medio de su id.
     */
    public UsuarioEntity getUsuario(String usuariosUsuario) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el usuario con el usuario = {0}", usuariosUsuario);
        UsuarioEntity usuarioEntity = persistence.find(usuariosUsuario);
        if (usuarioEntity == null) {
            LOGGER.log(Level.SEVERE, "El usuario con el usuario = {0} no existe", usuariosUsuario);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el usuario con el usuario = {0}", usuariosUsuario);
        return usuarioEntity;
    }

    public void actualizarToken(String usuario, String nuevoToken) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el token del usuario = {0}", usuario);

        persistence.actualizarToken(usuario, nuevoToken);

        LOGGER.log(Level.INFO, "Termina proceso de actualizar el token del usuario = {0}", usuario);
    }
}
