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
import org.springframework.web.util.HtmlUtils;

import es.ucm.fdi.iw.model.Proyecto;
import es.ucm.fdi.iw.model.ProyectoQueries;
import es.ucm.fdi.iw.model.Track;
import es.ucm.fdi.iw.model.TrackQueries;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.UserQueries;
import es.ucm.fdi.iw.util.HtmlEscapeStringEditor;

@Controller
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	private static final HtmlEscapeStringEditor sanitizer = new HtmlEscapeStringEditor();
	
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
	public String editor(@PathVariable long t, Model m, HttpSession s) {
		Track track = entityManager.find(Track.class, t);
		if(track == null){
			//track inexistente
			
			return "redirect:/";
		}
		
		User u = (User)s.getAttribute("user");
		if(track.getCreator().getId() != u.getId()){
			//no puedes modificar un track que no es tuyo
			
			return "redirect:/";
		}
			
		m.addAttribute("us", u.getName());
		
		m.addAttribute("track", track);
		
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
		
		String newName = sanitizer.sanitize(name);
		User u = new User();
		u.setName(newName);
		u.setEmail(HtmlUtils.htmlEscape(email.trim()));
		u.setPassword(passwordEncoder.encode(pass));
		
		u.setDescription(sanitizer.sanitize(desc));
		
		//u.setBandeja(new ArrayList<>());
		//u.setProjects(new ArrayList<>());
		//u.setCollaborations(new ArrayList<>());
		//u.setFriends(new ArrayList<>());
		
		u.setRoles("USER");
		logger.info("Nuevo usuario registrado: " + newName);
		this.entityManager.persist(u);
		
		return "redirect:/user/" + newName;
		
	}

	@GetMapping("/error")
	public String error(String error, Model m) {
		m.addAttribute("err", error);
		return "error";
	}
	
	
	@GetMapping("/project")
	public String project(){
		return "redirect:/";
	}
	
	
	@GetMapping("/project/{proyecto}")
	//@Transactional
	public String project(@PathVariable String proyecto, Model m, HttpSession s) {	
		//para poder tener proyectos con espacios en el nombre
		proyecto = proyecto.replace('_', ' ');
		
		Proyecto pro = ProyectoQueries.findWithName(entityManager,proyecto);
		if(pro==null){
			//TRATAMIENTO DE ERRORES
			
			return "redirect:/";
		}
		
		m.addAttribute("project", pro);
		m.addAttribute("us", ((User)s.getAttribute("user")).getName());
		return "project";
	}
	
	@GetMapping("/project/{proyecto}/pendingTracks")
	public String pending(@PathVariable String proyecto, Model m, HttpSession s) {	
		//para poder tener proyectos con espacios en el nombre
		proyecto = proyecto.replace('_', ' ');
		
		Proyecto pro = ProyectoQueries.findWithName(entityManager,proyecto);
		if(pro==null){
			//TRATAMIENTO DE ERRORES
			
			return "redirect:/";
		}
		
		User u = (User)s.getAttribute("user");
		if(pro.getAuthor().getId() != u.getId()){
			//no puedes ver tracks pendientes de un proyecto que no es tuyo
			
			return "redirect:/";
		}
		
		m.addAttribute("project", pro);
		return "pending";
	}
	
	@RequestMapping(value="/acceptTrack",method=RequestMethod.POST)
	@Transactional
	public String acceptTrack( HttpSession s,
			@RequestParam long track,
			@RequestParam long project){
		Proyecto pro = entityManager.find(Proyecto.class, project);
		if(pro == null){
			//proyecto inválido
			
			return "redirect:/";
		}
		
		Track tra = entityManager.find(Track.class, track);
		if (tra == null){
			//track inválido
			
			return "redirect:/";
		}
		
		//User user = (User)s.getAttribute("user");
		if (pro.getAuthor().getId() != ((User)s.getAttribute("user")).getId()){
			//no puedes aceptar tracks pendientes de un proyecto que no es tuyo
			
			return "redirect:/";
		}
		if (!pro.getPendingTracks().contains(tra)){
			//no es una canción que estuviese pendiente de aprobación
			
			return "redirect:/";
		}
		
		tra.setStatus(Track.ACTIVE);
		
		return "redirect:/" + tra.getName().replace(' ', '_');
	}
	
	@GetMapping("/addProject")
	public String addProject(){
		return "addProject";
	}
	
	
	/*CAMBIAR EL USER DEL FORMULARIO A LA SESIÓN*/
	@RequestMapping(value="/addProject", method=RequestMethod.POST)
	@Transactional
	public String addProject(@RequestParam(required = true) String title, @RequestParam(required = true) String desc, HttpSession s){
		if(! ProyectoQueries.nameAvailable(entityManager, title)){
			//Si ya hay un proyecto con ese nombre
			
			return "addProject";
		}
		//User usuario = UserQueries.findWithName(entityManager, user);
		User usuario = (User)s.getAttribute("user");
		usuario = entityManager.find(User.class, usuario.getId());
		
		
		Proyecto proy = new Proyecto();
		proy.setName(sanitizer.sanitize(title));
		proy.setDesc(sanitizer.sanitize(desc));
		proy.setAuthor(usuario);
		
		this.entityManager.persist(proy);
		logger.info("Proyecto agregado a la BD. Nombre de proyecto: " + title + ". Autor: " + usuario.getName());
		
		//List<Proyecto> proyectos = usuario.getProjects();
		//proyectos.add(proy);
		//usuario.setProjects(proyectos);
		usuario.getProjects().add(proy);
		
		//this.entityManager.persist(usuario);
		
		//para poder tener proyectos con espacios en el nombre
		return "redirect:/project/" + proy.getName().replace(' ', '_');
	}
	
	@RequestMapping(value="/addCollaborator", method=RequestMethod.POST)
	@Transactional
	public String addCollaborator(@RequestParam(required = true) String colaborador, 
			@RequestParam(required = true) long project,
			HttpSession s){
		
		//Primero comprobamos que sea un proyecto real
		Proyecto p = entityManager.find(Proyecto.class, project);
		
		if (p == null){
			//NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}
		
		//Luego comprobamos que sea un usuario existente
		User colab = UserQueries.findWithName(entityManager, colaborador);
		
		if(colab==null){
			//NO hay un usuario con ese nombre
			logger.info("Nombre de usuario NO registrado");
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}
		
		//También comprobamos que quien ha mandado la petición ha sido el creador del proyecto
		User creador = (User)s.getAttribute("user");
		creador = entityManager.find(User.class, creador.getId());
		
		if(p.getAuthor().getId() != creador.getId()){
			//No ha mandado la petición el creador
			
			return "redirect:/";
		}
		
		//Por último hay que mirar que no estuviese ya registrado como colaborador
		if (p.isCollaborator(colab)){
			//Ese colaborador ya está registrado
			logger.info("Nombre de colaborador ya registrado");
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}
		
		//Si llegamos aquí es que todo era correcto
		/*List<User> colabora = new ArrayList<User>();
		colabora =  p.getCollaborators();
		colabora.add(colab);
		p.setCollaborators(colabora);*/
		
		colab.getCollaborations().add(p);
		p.getCollaborators().add(colab);
		
		//entityManager.persist(colab);
		//entityManager.persist(p);
		
		logger.info("Redirigido. ¿TODO BIEN?");
		logger.info("num colaboradores:" + p.getCollaborators().size());
		//entityManager.flush();
		return "redirect:/project/" + p.getName().replace(' ', '_');
		
	}
	
	@RequestMapping(value="/addTrack", method=RequestMethod.POST)
	@Transactional
	public String addTrack(@RequestParam(required = true) String track, 
			@RequestParam(required = true) long project,
			HttpSession s){

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
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}
		Track nueva = new Track();
		nueva.setName(sanitizer.sanitize(track));
		if (p.getAuthor().getId() == u.getId() || p.isCollaborator(u)) {
			//al autor y los colaboradores se les pone directamente en la lista de tracks activas
			nueva.setStatus(Track.ACTIVE);
		} else {
			// si NO eres el creador ni colaborador
			nueva.setStatus(Track.PENDING);
		}
		nueva.setCreator(u);
		nueva.setProject(p);
		entityManager.persist(nueva);

		// tecnicamente no hace falta modificar también el del usuario
		
		entityManager.flush();
		logger.info("flush completado; nuevo id es " + nueva.getId());
		p.getTracks().add(nueva);
		

		return "redirect:/editor/" + nueva.getId();
	}
	
	@RequestMapping(value="/saveTrack", method=RequestMethod.POST)
	@Transactional
	public String saveTrack(@RequestParam(required = true) long track, @RequestParam(required = true) String abc/*,
			@RequestParam(required = true) long project*/,
			HttpSession s){
		
		Track t = entityManager.find(Track.class, track);
		if(t == null){
			//track inexistente
			
			return "redirect:/";
		}
		
		User u = (User)s.getAttribute("user");
		if(t.getCreator().getId() != u.getId()){
			//no puedes modificar un track que no es tuyo
			
			return "redirect:/";
		}
		
		t.setAbc(HtmlUtils.htmlEscape(abc.trim()));
		
		//se supone que al ser una copia viva se encarga el entity manager de guardarla actualizada
		//entityManager.persist(entityManager.find(Track.class, track));

		return "redirect:/project/" + t.getProject().getName().replace(' ', '_');
	}
	
	// Ejemplo : Reconocimiento de Usuario

	/*@GetMapping("/login/{role}")
	public String login(@PathVariable String role, HttpSession s) {
		s.setAttribute("role", role);
		return "login";
	}*/

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
