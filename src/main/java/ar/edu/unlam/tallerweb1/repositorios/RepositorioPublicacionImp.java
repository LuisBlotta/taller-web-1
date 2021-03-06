package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Compra;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPublicacion")
public class RepositorioPublicacionImp implements RepositorioPublicacion {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPublicacionImp(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void cargarPublicacion(Publicacion publicacion) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(publicacion);
    }

    @Override
    public List <Publicacion> buscarPublicacion(String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Publicacion.class)
                .createAlias("libro", "l")
                .createAlias("propietario", "p")
                .add(Restrictions.like("l.nombre", "%" + nombre + "%"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List <Publicacion> publicaciones = criteria.list();
        return publicaciones;
    }

    @Override
    public Publicacion buscarPublicacionPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        Publicacion publicacion = session.get(Publicacion.class, id);
        return publicacion;
    }

    @Override
    public List<Publicacion> listarPublicaciones(Usuario user) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Publicacion.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.ne("propietario", user));
        List <Publicacion> publicaciones = criteria.list();
        return publicaciones;
    }

    @Override
    public List<Publicacion> listarPublicacionesGenerico() {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Publicacion.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List <Publicacion> publicaciones = criteria.list();
        return publicaciones;
    }

    @Override
    public List<Publicacion> listarPublicacionesDeUsuario(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Publicacion.class)
                .add(Restrictions.eq("propietario", usuario))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List <Publicacion> publicaciones = criteria.list();
        return publicaciones;
    }

    @Override
    public void cargarCompra(Compra compra) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(compra);
    }

    @Override
    public Integer cantidadDeVentas(Long id, Usuario usuario){
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Compra.class)
                .createAlias("publicacion", "p")
                .add(Restrictions.eq("p.propietario.id", usuario.getId()))
                .add(Restrictions.eq("p.id", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List <Compra> ventas = criteria.list(); //compras que contienen la publicacion hecha por el usuario

        return ventas.size();
    }
}
