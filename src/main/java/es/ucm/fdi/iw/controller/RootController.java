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
	public String showUsuario(@PathVariable String name, Model m) {	
		if(!UserQueries.nameAvailable(entityManager, name)){
			User u = UserQueries.findWithName(entityManager, name);
			List<Proyecto> lista  = u.getProjects();

			m.addAttribute("user", u);
			m.addAttribute("lista",lista);
			
			return "user";
		} else {
			return "redirect:/home";
		}
		
	}

	/*@GetMapping("/search")
	public String search(Model m) {
		List<Proyecto> lista = ProyectoQueries.getProjectSearch(entityManager,"e");
		m.addAttribute("lista", lista);
		return "search";
	}*/
	
	@RequestMapping(value = "/search")
	public String search(@RequestParam("busqueda") String busqueda, Model m){
		List<Proyecto> lista = ProyectoQueries.getProjectSearch(entityManager,busqueda);
		m.addAttribute("lista", lista);
		return "search";
	}

	@GetMapping("/trendy")
	@Transactional
	public String trendy(Model m) {
		List<Proyecto> lista = ProyectoQueries.getTrendy(entityManager);
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
		List<User> lista = UserQueries.allUsers(entityManager);
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
		
		if(!UserQueries.nameAvailable(entityManager,name) || !UserQueries.emailAvailable(entityManager,email)){
			return "redirect:/home";
		}	
		
		User u = new User();
		u.setName(name);
		u.setEmail(email);
		u.setPassword(passwordEncoder.encode(pass));
		
		u.setDescription(desc);
		
		//u.setBandeja(new ArrayList<>());
		//u.setProjects(new ArrayList<>());
		//u.setCollaborations(new ArrayList<>());
		//u.setFriends(new ArrayList<>());
		
		u.setRoles("USER");
		logger.info("Nuevo usuario registrado: " + name);
		this.entityManager.persist(u);
		
		return "redirect:/user/" + name;
		
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/project")
	public String project(){
		return "redirect:/";
	}
	
	@GetMapping("/project/{proyecto}")
	@Transactional
	public String project(@PathVariable String proyecto, Model m) {	
		Proyecto pro = ProyectoQueries.findWithName(entityManager,proyecto);
		if(pro==null){
			//TRATAMIENTO DE ERRORES
			
			return "redirect:/";
		}
		
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
	public String addProject(@RequestParam(required = true) String title, @RequestParam(required = true) String desc, HttpSession s, Model m){
		if(! ProyectoQueries.nameAvailable(entityManager, title)){
			//Si ya hay un proyecto con ese nombre
			
			return "addProject";
		}
		//User usuario = UserQueries.findWithName(entityManager, user);
		User usuario = (User)s.getAttribute("user");
		usuario = entityManager.find(User.class, usuario.getId());
		
		Proyecto proy = new Proyecto();
		proy.setName(title);
		proy.setDesc(desc);
		proy.setAuthor(usuario);
		
		this.entityManager.persist(proy);
		logger.info("Proyecto agregado a la BD. Nombre de proyecto: " + title + ". Autor: " + usuario.getName());
		
		//List<Proyecto> proyectos = usuario.getProjects();
		//proyectos.add(proy);
		//usuario.setProjects(proyectos);
		usuario.getProjects().add(proy);
		
		//this.entityManager.persist(usuario);
		
		return "redirect:/project/" + proy.getName();
		//return project(proy.getName(),m);
	}
	
	@RequestMapping(value="/addCollaborator", method=RequestMethod.POST)
	@Transactional
	public String addCollaborator(@RequestParam(required = true) String colaborador, 
			@RequestParam(required = true) long project,
			HttpSession s, Model m){
		
		User colab = UserQueries.findWithName(entityManager, colaborador);
		// si lo necesitase "fresco": u = entityManager.find(User.class, u.getId());
		
		Proyecto p = entityManager.find(Proyecto.class, project);
		
		if (p == null){
			//NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}
		logger.info("num colaboradores:" + p.getCollaborators().size());
		if(colab==null){
			//NO hay un usuario con ese nombre
			logger.info("Nombre de usuario NO registrado");
			return "redirect:/";
		}
		if (p.isCollaborator(colab)){
			//Ese track ya está registrado
			logger.info("Nombre de track ya registrado");
			return "redirect:/project/" + p.getName();
		}
		
		
		/*List<User> colabora = new ArrayList<User>();
		colabora =  p.getCollaborators();
		colabora.add(colab);
		p.setCollaborators(colabora);*/
		
		colab.getCollaborations().add(p);
		p.getCollaborators().add(colab);
		
		entityManager.persist(colab);
		entityManager.persist(p);
		
		// tecnicamente no hace falta modificar también el del usuario ?????
		
		logger.info("Redirigido. ¿TODO BIEN?");
		logger.info("num colaboradores:" + p.getCollaborators().size());
		entityManager.flush();
		return "redirect:/project/" + p.getName();
		
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
		if (p.getAuthor().getId() == u.getId() || p.isCollaborator(u)) {
			//al autor y los colaboradores se les pone directamente en la lista de tracks activas
			nueva.setStatus("activa");
		} else {
			// si NO eres el creador ni colaborador
			nueva.setStatus("pendiente");
		}
		nueva.setCreator(u);
		entityManager.persist(nueva);

		// tecnicamente no hace falta modificar también el del usuario ?????
		
		logger.info("Redirigido. ¿TODO BIEN?");
		//return "redirect:/editor/" + nueva.getId();
		entityManager.flush();
		logger.info("flush completado; nuevo id es " + nueva.getId());

		if(p.getAuthor().getId() == u.getId() || p.isCollaborator(u)){
			//al autor y los colaboradores se les pone directamente en la lista de tracks activas
			p.getCurrentTracks().add(nueva);
		}else{
			//a los demás se les mete en la lista de pendientes
			p.getPendingTracks().add(nueva);
		}
		

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
