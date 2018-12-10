package resources;

import dtos.UsuarioDTO;
import entities.UsuarioEntity;
import exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import logic.UsuarioLogic;

@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());

    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Crea una nuevo usuario con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param usuarioDTO objeto con la informacion del nuevo usuario.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el usuario.
     */
    @POST
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario");
        //Desencriptar
        String usuario = usuarioDTO.getUsuario();
        String clave = usuarioDTO.getClave();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsuario(usuario);
        //generar el hash de la clave:
        String claveHash = "";
        usuarioEntity.setHashClave(claveHash);
        usuarioLogic.registrarUsuario(usuarioEntity); //Esto lanza BusinessLogic si ya existe un usuario.
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: output: true");
    }

    /**
     * Busca y devuelve todas los usuarioss que existen en la aplicacion.
     *
     * @return JSONArray {@link UsuarioDetailDTO} - Los usuarioss encontradas en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<UsuarioDTO> getUsuarios() {
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<String> listaUsuarios = usuarioLogic.getUsuarios();
        List<UsuarioDTO> lista = new ArrayList<>();
        for (String listaUsuario : listaUsuarios) {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsuario(listaUsuario);
            lista.add(usuario);
        }
        LOGGER.log(Level.INFO, "UsuarioResource getUsuarios: output: {0}", listaUsuarios.toString());
        return lista;
    }

    @GET
    @Path("login")
    public String hacerLogin(UsuarioDTO usuarioDTO) {

        String usuarioLogin = usuarioDTO.getUsuario();
        UsuarioEntity usuario = usuarioLogic.getUsuario(usuarioLogin);
        if (usuario != null) {
            String clave = usuarioDTO.getClave();
            String hashClave = usuario.getHashClave();
            //Verificar que sean iguales?
            if (clave.equals(hashClave)) {// Si son iguales:
                //Generar nuevo Token
                String token = "";
                usuarioLogic.actualizarToken(usuarioLogin, token);
                return token;
            }
        } else {
            throw new WebApplicationException("El recurso /clientes/" + usuarioLogin + " no existe.", 404);
        }
        return null;
    }

    @GET
    @Path("verificar/{usuario}/{token}") //Encriptar?
    public void verificarToken(@PathParam("usuario") String usuario, @PathParam("token") String token) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(usuario);
        if (!usuarioEntity.getUltimoToken().equals(token)) {
            throw new BusinessLogicException("Token Invalido!");
        }
    }
}
