package persistence;

import entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class UsuarioPersistence {

    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ArquisicionAuthPU");

    EntityManager em = emf.createEntityManager();

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param usuarioEntity objeto usuario que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public UsuarioEntity create(UsuarioEntity usuarioEntity) {
        LOGGER.log(Level.INFO, "Creando un usuario nuevo");
        em.getTransaction().begin();
        em.persist(usuarioEntity);
        em.getTransaction().commit();
        LOGGER.log(Level.INFO, "Saliendo de crear un usuario nuevo");
        return usuarioEntity;
    }

    /**
     * Devuelve todos los usuarios de la base de datos.
     *
     * @return una lista con todos los usuarios que encuentre en la base de
     * datos
     */
    public List<String> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los usuarios");
        TypedQuery query = em.createQuery("select u.usuario from UsuarioEntity u", String.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun usuario con el usuario que se envía de argumento
     *
     * @param usuariosUsuario: id correspondiente a el usuario buscado.
     * @return un usuario.
     */
    public UsuarioEntity find(String usuariosUsuario) {
        LOGGER.log(Level.INFO, "Consultando usuario con usuario = {0}", usuariosUsuario);
        return em.find(UsuarioEntity.class, usuariosUsuario);
    }

    public void actualizarToken(String usuario, String nuevoToken) {
        LOGGER.log(Level.INFO, "Actualizando el ultimo token del usuario = {0}", usuario);

        Query q = em.createQuery("Update UsuarioEntity p SET p.ultimoToken = :ultimoToken where p.usuario= :usuario");
        q.setParameter("ultimoToken", nuevoToken);
        q.setParameter("usuario", usuario);
        q.getResultList();
    }
}
