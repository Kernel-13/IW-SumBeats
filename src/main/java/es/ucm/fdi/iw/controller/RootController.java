package es.ucm.fdi.iw.controller;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.Proyecto;
import es.ucm.fdi.iw.model.ProyectoQueries;
import es.ucm.fdi.iw.model.Track;
import es.ucm.fdi.iw.model.TrackQueries;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.UserQueries;

@Controller
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping({ "/", "/index" })
	public String root(HttpSession s) {		
		return "home";
	}

	@GetMapping({ "/user", "/profile" })
	public String usuario() {
		return "user";
	}
	
	@GetMapping({ "/user/{name}", "/profile/{name}" })
	public String showusuario(@PathVariable String name, Model m) {
		UserQueries us = new UserQueries(entityManager);
		User u = us.findWithName(name);
		m.addAttribute("user", u);
		return "user";
	}

	/*
	 * @GetMapping("/admin") String admin(){ return "admin"; }
	 */

	@GetMapping("/search")
	public String search(Model m) {
		ProyectoQueries pq = new ProyectoQueries(entityManager);
		List<Proyecto> lista = pq.getProjectSearch("e");
		m.addAttribute("lista", lista);
		return "search";
	}

	@GetMapping("/project/{proyecto}")
	@Transactional
	public String project(@PathVariable String proyecto, Model m) {	
		ProyectoQueries pq = new ProyectoQueries(entityManager);
		Proyecto pro = pq.findWithName(proyecto);
		
		TrackQueries tr = new TrackQueries(entityManager);
		Track track = tr.findWithId(1);
		
		pro.setCurrentTracks(new ArrayList<>());
		for(int i = 0; i < 5; i++){
			pro.getCurrentTracks().add(track);
		}
		m.addAttribute("project", pro);
		return "project";
	}

	@GetMapping("/trendy")
	@Transactional
	public String trendy(Model m) {
		ProyectoQueries pq = new ProyectoQueries(entityManager);
		List<Proyecto> lista = pq.getTrendy();
		m.addAttribute("lista", lista);
		return "trendy";
	}

	@GetMapping("/studio")
	public String studio() {
		return "studio";
	}

	@GetMapping("/editor/{t}")
	public String editor(@PathVariable long t, Model m) {
		TrackQueries pq = new TrackQueries(entityManager);
		Track track = pq.findWithId(t);
		m.addAttribute("track", track);
		m.addAttribute("us", "bb");
		return "editor";
	}
	
	@GetMapping("/addSong")
	public String addSong() {
		return "addSong";
	}

	// Ejemplo : Reconocimiento de Usuario

	@GetMapping("/login/{role}")
	public String login(@PathVariable String role, HttpSession s) {
		s.setAttribute("role", role);
		return "login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
/*
	@GetMapping("/login")
	@ResponseBody
	@Transactional
	public String login(HttpSession s) {		
		ProyectoQueries pq = new ProyectoQueries(entityManager);
		List<Proyecto> lista = pq.getProjectSearch("e");
		return lista.toString();
	}	*/
	
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
}
