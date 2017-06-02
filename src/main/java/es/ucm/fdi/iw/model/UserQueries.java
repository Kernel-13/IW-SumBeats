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
	
}
