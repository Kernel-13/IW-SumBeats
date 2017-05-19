package es.ucm.fdi.iw.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller	
public class RootController {
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping({"/", "/index"})
	String root(HttpSession s) {
		return "home";
	}
	
	@GetMapping({"/user","/profile"})
	String usuario(){
		return "user";
	}
	
	/*@GetMapping("/admin")
	String admin(){
		return "admin";
	}*/
	
	@GetMapping("/search")
	String search(){
		return "search";
	}
	
	@GetMapping("/project")
	String project(){
		return "project";
	}
	
	@GetMapping("/trendy")
	String trendy(){
		return "trendy";
	}
	
	@GetMapping("/studio")
	String studio(){
		return "studio";
	}
	
	@GetMapping("/editor")
	String editor(){
		return "editor";
	}
	
	// Ejemplo : Reconocimiento de Usuario
	
	@GetMapping("/login/{role}")
	String login(@PathVariable String role, HttpSession s){
		s.setAttribute("role", role);
		return "login";
	}
	
	@GetMapping("/login")
	String login(){
		return "login";
	}
	
	@GetMapping("/logout")
	String login(HttpSession s){
		s.invalidate();
		return "login";
	}
}
