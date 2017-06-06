package es.ucm.fdi.iw.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.log4j.Logger;


public class TrackQueries {

	private static Logger log = Logger.getLogger(TrackQueries.class);

	public static int countWithName(EntityManager em, String name) {
		return em.createNamedQuery("trackByName").setParameter("name", name)
				.getResultList().size();
	}

}
