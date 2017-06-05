package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;


public class UserQueries {

	private static Logger log = Logger.getLogger(UserQueries.class);

	private EntityManager entityManager;
	
	public UserQueries(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public User findWithName(String name) {
		try {
			User p = entityManager.createQuery("from User t where t.name = :name", User.class)
					.setParameter("name", name)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user", e);
			return null;
		}
	}
	
	public User findWithEmail(String email) {
		try {
			User p = entityManager.createQuery("from User t where t.email = :email", User.class)
					.setParameter("email", email)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user", e);
			return null;
		}
	}
	
	public User findWithId(long id) {
		try {
			User p = entityManager.createQuery("from User t where t.id = :id", User.class)
					.setParameter("id", id)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el user", e);
			return null;
		}
	}
	
	public List<User> allUsers() {
		try {
			List<User> p = entityManager.createQuery("FROM User").getResultList();
			log.info("Nº Usuarios Query: " + p.size());
			return  p;
			
		} catch (Exception e) {
			log.info("No hay usuarios", e);
			return null;
		}
	}
	
	public boolean nameAvailable(String title) {
		return findWithName(title) == null;
	}
	
	public boolean emailAvailable(String email) {
		return findWithEmail(email) == null;
	}
	
}
