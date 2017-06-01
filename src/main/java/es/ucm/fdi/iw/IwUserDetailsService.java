package es.ucm.fdi.iw;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import es.ucm.fdi.iw.model.User;

public class IwUserDetailsService implements UserDetailsService {

	private static Logger log = Logger.getLogger(IwUserDetailsService.class);

    private EntityManager entityManager;
    
    @PersistenceContext
    public void setEntityManager(EntityManager em){
        this.entityManager = em;
    }

    public UserDetails loadUserByUsername(String username){
    	try {
	        User u = entityManager.createQuery("from User where name = :name", User.class)
	                            .setParameter("name", username)
	                            .getSingleResult();
	        // build UserDetails object
	        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
	        for (String r : u.getRoles().split("[,]")) {
	        	roles.add(new SimpleGrantedAuthority("ROLE_" + r));
		        log.info("Roles for " + username + " include " + roles.get(roles.size()-1));
	        }
	        return new org.springframework.security.core.userdetails.User(
	        		u.getName(), u.getPassword(), roles); 
	    } catch (Exception e) {
    		log.info("No such user: " + username);
    		return null;
    	}
    }
    
   /* public UserDetails ok(String p_name){
    	try {
	        User u = entityManager.createQuery("from Proyecto where name = :name", Proyecto.class)
	                            .setParameter("name", p_name)
	                            .getSingleResult();
	        // build UserDetails object
	        ArrayList<SimpleGrantedAuthority> roles = new ArrayList<>();
	        for (String r : u.getRoles().split("[,]")) {
	        	roles.add(new SimpleGrantedAuthority("ROLE_" + r));
		        log.info("Roles for " + p_name + " include " + roles.get(roles.size()-1));
	        }
	        return new org.springframework.security.core.userdetails.User(
	        		u.getName(), u.getPassword(), roles); 
	    } catch (Exception e) {
    		log.info("No such user: " + p_name);
    		return null;
    	}
    }*/
}