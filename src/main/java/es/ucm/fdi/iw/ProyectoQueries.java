package es.ucm.fdi.iw;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import es.ucm.fdi.iw.model.Proyecto;


public class ProyectoQueries {

	private static Logger log = Logger.getLogger(IwUserDetailsService.class);

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public Proyecto findWithName(String name) {
		try {
			Proyecto p = entityManager.createQuery("from Proyecto where name = :name", Proyecto.class)
					.setParameter("name", name)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el proyecto");
			return null;
		}
	}
	
	public List<Proyecto> getTrendy() {
		try {
			TypedQuery<Proyecto> query = entityManager.createQuery("from Proyecto order by week_rating", Proyecto.class)
					.setMaxResults(10);
			List<Proyecto> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			log.info("No existen proyectos");
			return null;
		}
	}
	
	public List<Proyecto> getProjectSearch(String search) {
		try {
			TypedQuery<Proyecto> query = entityManager.createQuery("from Proyecto where name LIKE :text order by name", Proyecto.class)
					.setParameter("text", search)
					.setMaxResults(100);
			List<Proyecto> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			log.info("No existen proyectos que contengan la palabra clave");
			return null;
		}
	}
	
}
