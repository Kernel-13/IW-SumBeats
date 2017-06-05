package es.ucm.fdi.iw.controller;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping({ "/", "/index" })
	public String root(HttpSession s, Principal p) {
		s.setAttribute("user", entityManager.createQuery(
				"from User where name = :name", User.class).setParameter(
						"name", p.getName()).getSingleResult());
		return "home";
	}

	@GetMapping({ "/user/{name}", "/profile/{name}" })
	public String showusuario(@PathVariable String name, Model m) {
		UserQueries us = new UserQueries(entityManager);
		
		if(!us.nameAvailable(name)){
			User u = us.findWithName(name);
			List<Proyecto> lista  = u.getProjects();

			m.addAttribute("user", u);
			m.addAttribute("lista",lista);
			
			return "user";
		} else {
			return "error";
		}
		
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
		m.addAttribute("track", entityManager.find(Track.class, t));
		m.addAttribute("us", "bb");
		return "editor";
	}
	
	@GetMapping("/users")
	public String users(Model m) {
		UserQueries uq = new UserQueries(entityManager);
		List<User> lista = uq.allUsers();
		m.addAttribute("lista", lista);
		logger.info("Nº Usuarios Controller: " + lista.size());
		return "users";
	}
	
	@GetMapping("/addUser")
	public String addUser() {
		return "addUser";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@Transactional
	public String addUser(@RequestParam(required = true) String name, 
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String pass, 
			@RequestParam(required = true) String desc) {
		UserQueries uq = new UserQueries(entityManager);
		
		if(uq.nameAvailable(name) && uq.emailAvailable(email)){
			User u = new User();
			u.setName(name);
			u.setEmail(email);

			u.setPassword(passwordEncoder.encode(pass));
			
			u.setDescription(desc);
			u.setBandeja(new ArrayList<>());
			u.setProjects(new ArrayList<>());
			u.setCollaborations(new ArrayList<>());
			u.setFriends(new ArrayList<>());
			u.setRoles("USER");

			logger.info(name);
			this.entityManager.persist(u);
			
			return "redirect:/user/" + name;
		} else {
			return "error";
		}
		
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/project/{proyecto}")
	@Transactional
	public String project(@PathVariable String proyecto, Model m) {	
		ProyectoQueries pq = new ProyectoQueries(this.entityManager);
		Proyecto pro = pq.findWithName(proyecto);
		if(pro==null){
			//TRATAMIENTO DE ERRORES
			
			return "home";
		}
		
		/*TrackQueries tr = new TrackQueries(this.entityManager);
		Track track = tr.findWithId(1);
		
		pro.setCurrentTracks(new ArrayList<>());
		for(int i = 0; i < 5; i++){
			pro.getCurrentTracks().add(track);
		}*/
		m.addAttribute("project", pro);
		return "project";
	}
	
	@GetMapping("/addProject")
	public String addProject(){
		return "addProject";
	}
	
	/*CAMBIAR EL USER DEL FORMULARIO A LA SESIÓN*/
	@RequestMapping(value="/addProject", method=RequestMethod.POST)
	@Transactional
	public String addProject(@RequestParam(required = true) String title, @RequestParam(required = true) String desc, @RequestParam(required = true) String user, HttpSession s, Model m){
		if(! new ProyectoQueries(this.entityManager).nameAvailable(title)){
			//Si ya hay un proyecto con ese nombre
			
			return "addProject";
		}
		Proyecto proy = new Proyecto();
		proy.setName(title);
		proy.setDesc(desc);
		User usuario = new UserQueries(this.entityManager).findWithName(user);
		proy.setAuthor(usuario);
		
		this.entityManager.persist(proy);
		logger.info("Proyecto agregado a la BD. Nombre de proyecto: " + title + ". Autor: " + user);
		
		List<Proyecto> proyectos = usuario.getProjects();
		proyectos.add(proy);
		usuario.setProjects(proyectos);
		
		this.entityManager.persist(usuario);
		
		return "redirect:/project/" + proy.getName();
		//return project(proy.getName(),m);
	}
	
	@RequestMapping(value="/addTrack", method=RequestMethod.POST)
	@Transactional
	public String addTrack(@RequestParam(required = true) String track, 
			@RequestParam(required = true) long project,
			HttpSession s, Model m){

		User u = (User)s.getAttribute("user");
		// si lo necesitase "fresco": u = entityManager.find(User.class, u.getId());

		Proyecto p = entityManager.find(Proyecto.class, project);
		if (p == null){
			//NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}
		if (TrackQueries.countWithName(entityManager, track) > 0){
			//Ese track ya está registrado
			logger.info("Nombre de track ya registrado");
			return "redirect:/project/" + project;
		}
		Track nueva = new Track();
		nueva.setName(track);
		if (p.getAuthor().getId() == u.getId()) {
			// eres el creador: auto-activado!
			nueva.setStatus("activa");
		} else {
			// si NO eres el creador ¿¿¿¿ y si ya eres colaborador ????
			nueva.setStatus("pendiente");
		}
		nueva.setCreator(u);
		entityManager.persist(nueva);
		p.getCurrentTracks().add(nueva);

		// tecnicamente no hace falta modificar también el del usuario ?????
		
		logger.info("Redirigido. ¿TODO BIEN?");
		//return "redirect:/editor/" + nueva.getId();
		entityManager.flush();
		logger.info("flush completado; nuevo id es " + nueva.getId());


		return "redirect:/editor/" + nueva.getId();
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
	 * @GetMapping("/login")
	 * 
	 * @ResponseBody
	 * 
	 * @Transactional public String login(HttpSession s) { ProyectoQueries pq =
	 * new ProyectoQueries(entityManager); List<Proyecto> lista =
	 * pq.getProjectSearch("e"); return lista.toString(); }
	 */

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
}
