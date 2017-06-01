package es.ucm.fdi.iw.controller;

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

import es.ucm.fdi.iw.model.Proyecto;
import es.ucm.fdi.iw.model.User;

@Controller	
public class RootController {
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping({"/", "/index"})
	public String root(HttpSession s) {
		return "home";
	}
	
	@GetMapping({"/user","/profile"})
	public String usuario(){
		return "user";
	}
	
	/*@GetMapping("/admin")
	String admin(){
		return "admin";
	}*/
	
	@GetMapping("/search")
	public String search(){
		return "search";
	}
	
	@GetMapping("/project")
	@Transactional
	public String project(Model m){
		Proyecto p = new Proyecto();
		p.setName("Mi Proyecto");
		p.setDesc("Mi descripcion");
		p.setCollaborators(new ArrayList<>());
		p.getCollaborators().add((User)entityManager.find(User.class, 1L));
		entityManager.persist(p);
		m.addAttribute("project", p);
		return "project";
	}
	
	@GetMapping("/trendy")
	public String trendy(){
		return "trendy";
	}
	
	@GetMapping("/studio")
	public String studio(){
		return "studio";
	}
	
	@GetMapping("/editor")
	public String editor(){
		return "editor";
	}
	
	// Ejemplo : Reconocimiento de Usuario
	
	@GetMapping("/login/{role}")
	public String login(@PathVariable String role, HttpSession s){
		s.setAttribute("role", role);
		return "login";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(){
		return "logout";
	}
}
