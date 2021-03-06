package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Compra;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.Puntaje;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// implelemtacion del repositorio de usuarios, la anotacion @Repository indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.dao
// para encontrar esta clase.
@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

	// Como todo repositorio maneja acciones de persistencia, normalmente estara inyectado el session factory de hibernate
	// el mismo esta difinido en el archivo hibernateContext.xml
	private SessionFactory sessionFactory;

    @Autowired
	public RepositorioUsuarioImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Usuario consultarUsuario(Usuario usuario) {
		// Se obtiene la sesion asociada a la transaccion iniciada en el servicio que invoca a este metodo y se crea un criterio
		// de busqueda de Usuario donde el email y password sean iguales a los del objeto recibido como parametro
		// uniqueResult da error si se encuentran mas de un resultado en la busqueda.
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.add(Restrictions.eq("password", usuario.getPassword()))
				.uniqueResult();
	}
	@Override
	public void cargarUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		session.save(usuario);
	}

	@Override
	public Usuario getUsuarioRegalo(String email) {
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

	@Override
	public List<Compra> getCompras(Usuario usuario){
		final Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Compra.class)
				.add(Restrictions.eq("usuario", usuario))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List <Compra> compras = criteria.list();
		return compras;
	}

	@Override
	public List<Publicacion> getVentas(Usuario usuario){
		final Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Compra.class)
				.createAlias("publicacion", "p")
				.add(Restrictions.eq("p.propietario.id", usuario.getId()));

		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("publicacion"))
				.add(Projections.rowCount()));
		List <Publicacion> ventas = criteria.list();

		return ventas;
	}

	@Override
	public Boolean tieneCompra(Long usuarioId, Long publicacionId) {
		final Session session = sessionFactory.getCurrentSession();
		Compra compra = (Compra) session.createCriteria(Compra.class)
				.add(Restrictions.eq("usuario.id", usuarioId))
				.add(Restrictions.eq("publicacion.id", publicacionId))
				.uniqueResult();
		if(compra != null){
			return true;
		}
		return false;
	}

	@Override
	public List<Puntaje> listarComprasConPuntajePorUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Puntaje.class, "punt")
				.add(Restrictions.eq("punt.usuario", usuario))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List <Puntaje> puntajes = criteria.list();
		return puntajes;
	}

	@Override
	public Boolean tienePublicacion(Long usuarioId, Long publicacionId) {
		final Session session = sessionFactory.getCurrentSession();
		Publicacion publicacion = (Publicacion) session.createCriteria(Publicacion.class)
				.add(Restrictions.eq("propietario.id", usuarioId))
				.add(Restrictions.eq("id", publicacionId))
				.uniqueResult();
		if(publicacion != null){
			return true;
		}
		return false;
	}}
