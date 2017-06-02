package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;


public class TrackQueries {

	private static Logger log = Logger.getLogger(TrackQueries.class);

	private EntityManager entityManager;
	
	public TrackQueries(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Track findWithName(String name) {
		try {
			Track p = entityManager.createQuery("from Track t where t.name = :name", Track.class)
					.setParameter("name", name)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el track", e);
			return null;
		}
	}
	
	public Track findWithId(long id) {
		try {
			Track p = entityManager.createQuery("from Track t where t.id = :id", Track.class)
					.setParameter("id", id)
					.getSingleResult();

			return p;
		} catch (Exception e) {
			log.info("No existe el track", e);
			return null;
		}
	}
	
}
