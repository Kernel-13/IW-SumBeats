package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

public class ProyectoQueries {

	private static Logger log = Logger.getLogger(ProyectoQueries.class);

	//private EntityManager entityManager;

//	public ProyectoQueries(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}

	public static Proyecto findWithName(EntityManager entityManager, String name) {
		try {
			Proyecto p = entityManager.createQuery("from Proyecto p where p.name = :name", Proyecto.class)
					.setParameter("name", name).getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el proyecto", e);
			return null;
		}
	}

	public static List<Proyecto> getTrendy(EntityManager entityManager) {
		try {
			TypedQuery<Proyecto> query = entityManager
					.createQuery("from Proyecto p order by p.weekRating desc", Proyecto.class).setMaxResults(10);
			List<Proyecto> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			log.info("No existen proyectos", e);
			return null;
		}
	}

	public static List<Proyecto> getProjectSearch(EntityManager entityManager, String search) {
		try {
			TypedQuery<Proyecto> query = entityManager
					.createQuery("from Proyecto p where name LIKE CONCAT('%',:text,'%') order by name", Proyecto.class)
					.setParameter("text", search).setMaxResults(100);
			List<Proyecto> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			log.info("No existen proyectos que contengan la palabra clave", e);
			return new ArrayList<Proyecto>();
		}
	}

	public static boolean nameAvailable(EntityManager entityManager, String title) {
		return findWithName(entityManager, title) == null;
	}

	public static void increasePoints(EntityManager entityManager, String name) {
		try {
			entityManager.createQuery("update Proyecto p set p.weekRating = p.weekRating+1 Where p.name = :name",
					Proyecto.class).setParameter("name", name);
			entityManager.createQuery("update Proyecto p set p.weekRating = p.globalRating+1 Where p.name = :name",
					Proyecto.class).setParameter("name", name);
		} catch (Exception e) {
			log.info("No existe el proyecto", e);
		}
	}
	
}
