package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;


public class UserQueries {

	private static Logger log = Logger.getLogger(UserQueries.class);

//	private EntityManager entityManager;
	
//	public UserQueries(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
	
	public static User findWithName(EntityManager entityManager, String name) {
		try {
			User p = entityManager.createQuery("from User t where t.name = :name", User.class)
					.setParameter("name", name)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user con nombre: " + name, e);
			return null;
		}
	}
	
	public static User findWithEmail(EntityManager entityManager, String email) {
		try {
			User p = entityManager.createQuery("from User t where t.email = :email", User.class)
					.setParameter("email", email)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user con email: " + email, e);
			return null;
		}
	}
	
	public static User findWithId(EntityManager entityManager, long id) {
		try {
			User p = entityManager.createQuery("from User t where t.id = :id", User.class)
					.setParameter("id", id)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user con id: " +  id, e);
			return null;
		}
	}
	
	public static List<User> allUsers(EntityManager entityManager) {
		try {
			List<User> p = entityManager.createQuery("FROM User").getResultList();
			log.info("Nº Usuarios Query: " + p.size());
			return  p;
			
		} catch (Exception e) {
			log.info("No hay usuarios", e);
			return null;
		}
	}
	
	public static boolean nameAvailable(EntityManager entityManager, String title) {
		return findWithName(entityManager, title) == null;
	}
	
	public static boolean emailAvailable(EntityManager entityManager, String email) {
		return findWithEmail(entityManager, email) == null;
	}
	
}
