package es.ucm.fdi.iw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import es.ucm.fdi.iw.model.Comentario;
import es.ucm.fdi.iw.model.Correo;
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
		s.setAttribute("user", entityManager.createQuery("from User where name = :name", User.class)
				.setParameter("name", p.getName()).getSingleResult());
		return "home";
	}

	@GetMapping({ "/user/{name}", "/profile/{name}" })
	public String showUsuario(@PathVariable String name, Model m) {
		if (!UserQueries.nameAvailable(entityManager, name)) {
			User u = UserQueries.findWithName(entityManager, name);
			List<Proyecto> lista = u.getProjects();

			m.addAttribute("user", u);
			m.addAttribute("lista", lista);

			return "user";
		} else {
			return "redirect:/home";
		}

	}

	/*
	 * @GetMapping("/search") public String search(Model m) { List<Proyecto>
	 * lista = ProyectoQueries.getProjectSearch(entityManager,"e");
	 * m.addAttribute("lista", lista); return "search"; }
	 */

	@RequestMapping(value = "/search")
	public String search(@RequestParam("busqueda") String busqueda, Model m) {
		String search = Jsoup.parse(busqueda).text();
		List<Proyecto> lista = ProyectoQueries.getProjectSearch(entityManager, search);
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
		if (track == null) {
			// track inexistente
			return "redirect:/";
		}

		User u = (User) s.getAttribute("user");
		if (track.getCreator().getId() != u.getId() && u.getId() != track.getProject().getAuthor().getId()) {
			// no puedes modificar un track que no es tuyo
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
	public String addUser(Model m) {
		m.addAttribute("n", 20);
		return "addUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@Transactional
	public String addUser(@RequestParam(required = true) String name, 
			@RequestParam(required = true) String email,
			@RequestParam(required = true) String pass, 
			@RequestParam(required = true) String desc,
			@RequestParam(required = true) int foto ) {

		if (!UserQueries.nameAvailable(entityManager, name) || !UserQueries.emailAvailable(entityManager, email)) {
			return "redirect:/home";
		}

		String newName = Jsoup.parse(name).text();
		User u = new User();
		u.setName(newName);
		u.setEmail(HtmlUtils.htmlEscape(email.trim()));
		u.setPassword(passwordEncoder.encode(pass));
		u.setIcon(foto);
		
		u.setDescription(Jsoup.parse(desc).text());

		u.setRoles("USER");
		logger.info("Nuevo usuario registrado: " + newName);
		this.entityManager.persist(u);

		return "redirect:/";

	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}

	@GetMapping("/project")
	public String project() {
		return "redirect:/";
	}

	@GetMapping("/project/{proyecto}")
	// @Transactional
	public String project(@PathVariable String proyecto, Model m, HttpSession s) {

		proyecto = proyecto.replace('_', ' '); // Para poder tener proyectos con
												// espacios en el nombre
		Proyecto p = ProyectoQueries.findWithName(entityManager, proyecto);

		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());

		if (p == null) {
			// TRATAMIENTO DE ERRORES

			return "redirect:/";
		}

		boolean likeable = !u.getLiked().contains(p);

		
		String combined = "";
		if (!p.getTracks().isEmpty()) {
			String allTracks = "";
			String header = "";
			boolean headerFilled = false;
			int voice = 1;
			for (Track t : p.getTracks()) {
				boolean notationFound = false;
				String aux = t.getAbc();
				for (char ch : aux.toCharArray()) {
					if (ch == '|' && !notationFound) {
						notationFound = true;
						allTracks += "V:" + voice + "\n";
					}
					if (notationFound) {
						allTracks += ch;
					} else {
						if (!headerFilled) {
							header += ch;
						}
					}
				}
				headerFilled = true;
				voice++;
			}
			combined = header + allTracks;
		}
		
		m.addAttribute("project", p);
		m.addAttribute("likeable", likeable);
		m.addAttribute("combined", combined);
		m.addAttribute("us", ((User) s.getAttribute("user")).getName());
		return "project";
	}

	@GetMapping("/project/{proyecto}/pendingTracks")
	public String pending(@PathVariable String proyecto, Model m, HttpSession s) {
		// para poder tener proyectos con espacios en el nombre
		proyecto = proyecto.replace('_', ' ');

		Proyecto pro = ProyectoQueries.findWithName(entityManager, proyecto);
		if (pro == null) {
			// TRATAMIENTO DE ERRORES

			return "redirect:/";
		}

		User u = (User) s.getAttribute("user");
		if (pro.getAuthor().getId() != u.getId()) {
			// no puedes ver tracks pendientes de un proyecto que no es tuyo

			return "redirect:/";
		}

		m.addAttribute("project", pro);
		return "pending";
	}

	@RequestMapping(value = "/acceptTrack", method = RequestMethod.POST)
	@Transactional
	public String acceptTrack(HttpSession s, @RequestParam long track, @RequestParam long project) {
		Proyecto pro = entityManager.find(Proyecto.class, project);
		if (pro == null) {
			// proyecto inválido

			return "redirect:/";
		}

		Track tra = entityManager.find(Track.class, track);
		if (tra == null) {
			// track inválido

			return "redirect:/";
		}

		// User user = (User)s.getAttribute("user");
		if (pro.getAuthor().getId() != ((User) s.getAttribute("user")).getId()) {
			// no puedes aceptar tracks pendientes de un proyecto que no es tuyo

			return "redirect:/";
		}
		if (!pro.getPendingTracks().contains(tra)) {
			// no es una canción que estuviese pendiente de aprobación

			return "redirect:/";
		}

		tra.setStatus(Track.ACTIVE);

		return "redirect:/project/" + pro.getName().replace(' ', '_');
	}

	@GetMapping("/addProject")
	public String addProject(Model m) {
		m.addAttribute("n", 20);
		return "addProject";
	}

	/* CAMBIAR EL USER DEL FORMULARIO A LA SESIÓN */
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	@Transactional
	public String addProject(@RequestParam(required = true) String title, @RequestParam(required = true) String desc,
			@RequestParam(required = true) int foto, HttpSession s) {
		if (!ProyectoQueries.nameAvailable(entityManager, title)) {
			// Si ya hay un proyecto con ese nombre

			return "addProject";
		}
		// User usuario = UserQueries.findWithName(entityManager, user);
		User usuario = (User) s.getAttribute("user");
		usuario = entityManager.find(User.class, usuario.getId());

		Proyecto proy = new Proyecto();
		proy.setName(Jsoup.parse(title).text());
		proy.setDesc(Jsoup.parse(desc).text());
		proy.setAuthor(usuario);
		proy.setIcon(foto);

		this.entityManager.persist(proy);
		logger.info("Proyecto agregado a la BD. Nombre de proyecto: " + title + ". Autor: " + usuario.getName());

		// List<Proyecto> proyectos = usuario.getProjects();
		// proyectos.add(proy);
		// usuario.setProjects(proyectos);
		usuario.getProjects().add(proy);

		// this.entityManager.persist(usuario);

		// para poder tener proyectos con espacios en el nombre
		return "redirect:/project/" + proy.getName().replace(' ', '_');
	}

	@RequestMapping(value = "/addCollaborator", method = RequestMethod.POST)
	@Transactional
	public String addCollaborator(@RequestParam(required = true) String colaborador,
			@RequestParam(required = true) long project, HttpSession s) {

		// Primero comprobamos que sea un proyecto real
		Proyecto p = entityManager.find(Proyecto.class, project);

		if (p == null) {
			// NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}

		// Luego comprobamos que sea un usuario existente
		User colab = UserQueries.findWithName(entityManager, colaborador);

		if (colab == null) {
			// NO hay un usuario con ese nombre
			logger.info("Nombre de usuario NO registrado");
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}

		// También comprobamos que quien ha mandado la petición ha sido el
		// creador del proyecto
		User creador = (User) s.getAttribute("user");
		creador = entityManager.find(User.class, creador.getId());

		if (p.getAuthor().getId() != creador.getId()) {
			// No ha mandado la petición el creador

			return "redirect:/";
		}

		// Por último hay que mirar que no estuviese ya registrado como
		// colaborador
		if (p.isCollaborator(colab)) {
			// Ese colaborador ya está registrado
			logger.info("Nombre de colaborador ya registrado");
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}

		colab.getCollaborations().add(p);
		p.getCollaborators().add(colab);

		logger.info("Redirigido. ¿TODO BIEN?");
		logger.info("num colaboradores:" + p.getCollaborators().size());

		return "redirect:/project/" + p.getName().replace(' ', '_');

	}

	@RequestMapping(value = "/addTrack", method = RequestMethod.POST)
	@Transactional
	public String addTrack(@RequestParam(required = true) String track, @RequestParam(required = true) long project,
			HttpSession s) {

		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());

		Proyecto p = entityManager.find(Proyecto.class, project);
		if (p == null) {
			// NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}
		if (TrackQueries.countWithName(entityManager, track) > 0) {
			// Ese track ya está registrado
			logger.info("Nombre de track ya registrado");
			return "redirect:/project/" + p.getName().replace(' ', '_');
		}
		Track nueva = new Track();
		nueva.setName(Jsoup.parse(track).text());
		if (p.getAuthor().getId() == u.getId() || p.isCollaborator(u)) {
			// al autor y los colaboradores se les pone directamente en la lista
			// de tracks activas
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
		u.getTracks().add(nueva);

		return "redirect:/editor/" + nueva.getId();
	}

	@RequestMapping(value = "/saveTrack", method = RequestMethod.POST)
	@Transactional
	public String saveTrack(@RequestParam(required = true) long track, @RequestParam(required = true) String abc,
			HttpSession s) {

		Track t = entityManager.find(Track.class, track);
		if (t == null) {
			// track inexistente

			return "redirect:/";
		}

		User u = (User) s.getAttribute("user");
		if (t.getCreator().getId() != u.getId() && t.getProject().getAuthor().getId() != u.getId()) {
			// no puedes modificar un track que no es tuyo

			return "redirect:/";
		}

		// t.setAbc(HtmlUtils.htmlEscape(abc.trim()));
		t.setAbc(abc);

		if (t.getStatus() == Track.ACTIVE) {
			return "redirect:/project/" + t.getProject().getName().replace(' ', '_');
		} else if (t.getStatus() == Track.PENDING && t.getProject().getAuthor().getId() == u.getId()) {
			return "redirect:/project/" + t.getProject().getName().replace(' ', '_') + "/pendingTracks";
		} else {
			return "redirect:/project/" + t.getProject().getName().replace(' ', '_');
		}
	}

	@RequestMapping(value = "/deleteTrack", method = RequestMethod.POST)
	@Transactional
	public String deleteTrack(@RequestParam long track, HttpSession s) {

		Track t = entityManager.find(Track.class, track);
		if (t == null) {
			logger.info("No Borrado - No existe el track");
			return "redirect:/"; // Track Inexistente
		}

		Proyecto p = entityManager.find(Proyecto.class, t.getProject().getId()); // "Fresh"

		User u = (User) s.getAttribute("user");
		if (t.getCreator().getId() != u.getId() && t.getProject().getAuthor().getId() != u.getId()) {
			logger.info("No Borrado - No eres el autor del Track / Proyecto");
			return "redirect:/"; // Can't delete a Track if it is not yours
		}
		u = entityManager.find(User.class, u.getId());
		u.getTracks().remove(t);
		p.getTracks().remove(t);
		entityManager.remove(t);
		logger.info("Track Borrado - RootController");

		return "redirect:/project/" + p.getName().replace(' ', '_');
	}

	@RequestMapping(value = "/deleteCollaborator", method = RequestMethod.POST)
	@Transactional
	public String deleteCollaborator(@RequestParam long pro, @RequestParam long user, HttpSession s) {

		Proyecto p = entityManager.find(Proyecto.class, pro);
		if (p == null) {
			logger.info("No Borrado - No existe el Proyecto");
			return "redirect:/";
		}

		User u = (User) s.getAttribute("user");
		if (p.getAuthor().getId() != u.getId()) {
			logger.info("No Borrado - No eres el autor del Proyecto");
			return "redirect:/";
		}

		User uc = entityManager.find(User.class, user);
		if (!p.getCollaborators().contains(uc)) {
			logger.info("No Borrado - No existe el colaborador en el proyecto");
			return "redirect:/";
		}

		p.getCollaborators().remove(uc);
		uc.getCollaborations().remove(p);
		logger.info("Colaborador Borrado - RootController");

		return "redirect:/project/" + p.getName().replace(' ', '_');
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	@Transactional
	public String deleteProject(@RequestParam long proyecto, HttpSession s) {

		Proyecto p = entityManager.find(Proyecto.class, proyecto);
		if (p == null) {
			logger.info("No Borrado - No existe el Proyecto");
			return "redirect:/";
		}

		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());

		if (p.getAuthor().getId() != u.getId()) {
			logger.info("No Borrado - No eres el propietario del Proyecto");
			return "redirect:/";
		}

		u.getProjects().remove(p);
		logger.info("Proyecto Borrado de Usuario");

		while (!p.getCollaborators().isEmpty()) {
			User ux = entityManager.find(User.class, p.getCollaborators().get(0).getId());
			ux.getCollaborations().remove(p);
			p.getCollaborators().remove(0);
			logger.info("Colaboracion Borrada de Usuario " + ux.getName());
		}

		while (!p.getLovers().isEmpty()) {
			User ux = entityManager.find(User.class, p.getLovers().get(0).getId());
			ux.getLiked().remove(p);
			p.getLovers().remove(0);
			logger.info("Like Borrado de Usuario " + ux.getName());
		}

		while (!p.getComments().isEmpty()) {
			Comentario c = entityManager.find(Comentario.class, p.getComments().get(0).getId());
			entityManager.remove(c);
			p.getComments().remove(0);
		}

		logger.info("Colaboradores y comentarios Borrados- clear()");

		while (!p.getTracks().isEmpty()) {
			Track t = entityManager.find(Track.class, p.getTracks().get(0).getId());
			entityManager.remove(t);
			p.getTracks().remove(0);
		}

		entityManager.remove(p);
		logger.info("Proyecto Borrado - RootController.entityManager.remove(p)");

		return "redirect:/user/" + u.getName().replace(' ', '_');
	}

	@GetMapping("/my_tracks")
	public String myTracks(Model m, HttpSession s) {
		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());

		List<Track> lista = u.getTracks();
		m.addAttribute("lista", lista);
		logger.info("Nº Tracks del Usuario: " + lista.size());
		return "/my_tracks";
	}

	@GetMapping("/customization")
	public String customization(Model m, HttpSession s) {
		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());
		m.addAttribute("user", u);
		m.addAttribute("n", 20);
		return "customization";
	}

	@RequestMapping(value = "/customization", method = RequestMethod.POST)
	@Transactional
	public String changeInfo(@RequestParam String desc, @RequestParam int foto, HttpSession s) {

		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());
		u.setDescription(Jsoup.parse(desc).text());
		u.setIcon(foto);
		logger.info("Descripcion Modificada");

		return "redirect:/user/" + u.getName().replace(' ', '_');

	}

	@RequestMapping(value = "/addLike", method = RequestMethod.POST)
	@Transactional
	public String addLike(@RequestParam long proyecto, HttpSession s) {

		User u = (User) s.getAttribute("user");
		u = entityManager.find(User.class, u.getId());
		Proyecto p = entityManager.find(Proyecto.class, proyecto);

		if (p == null) {
			logger.info("Invalid Project");
			return "redirect:/";
		}

		if (!u.getLiked().contains(p)) {
			u.getLiked().add(p);
			p.setGlobalRating(p.getGlobalRating() + 1);
			p.setWeekRating(p.getWeekRating() + 1);
		} else {
			u.getLiked().remove(p);
			p.setGlobalRating(p.getGlobalRating() - 1);
			p.setWeekRating(p.getWeekRating() - 1);
		}

		return "redirect:/project/" + p.getName();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file";
			} catch (Exception e) {
				return "You failed to upload => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}

	// Ejemplo : Reconocimiento de Usuario

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

	@RequestMapping(value = "/addComent", method = RequestMethod.POST)
	@Transactional
	public String addComent(@RequestParam(required = true) String coment, @RequestParam(required = true) long project,
			HttpSession s) {

		User u = (User) s.getAttribute("user");
		// si lo necesitase "fresco": u = entityManager.find(User.class,
		// u.getId());

		Proyecto p = entityManager.find(Proyecto.class, project);

		if (p == null) {
			// NO hay un proyecto con ese nombre
			logger.info("Nombre de proyecto NO registrado");
			return "redirect:/";
		}

		Comentario nueva = new Comentario();
		nueva.setAutor(u);
		nueva.setProyecto(p);

		// nueva.setMessage(sanitizer.sanitize(coment));
		nueva.setMessage(Jsoup.parse(coment).text());

		entityManager.persist(nueva);

		// tecnicamente no hace falta modificar también el del usuario

		entityManager.flush();
		logger.info("flush completado; nuevo id es " + nueva.getId());
		p.getComments().add(nueva);

		return "redirect:/project/" + p.getName().replace(' ', '_');
	}

	@RequestMapping(value = "/deleteComent", method = RequestMethod.POST)
	@Transactional
	public String deleteComentario(@RequestParam long coment, @RequestParam(required = true) long pro, HttpSession s) {

		Comentario t = entityManager.find(Comentario.class, coment);
		if (t == null) {
			logger.info("No Borrado - No existe el comentario");
			return "redirect:/"; // comentario Inexistente
		}

		Proyecto p = entityManager.find(Proyecto.class, pro); // "Fresh"

		User u = (User) s.getAttribute("user");
		if (t.getAutor().getId() != u.getId()) {
			logger.info("No Borrado - No eres el autor del comentario");
			return "redirect:/"; // Can't delete a comment if it is not yours
		}

		p.getComments().remove(t);
		entityManager.remove(t);
		logger.info("Comentario Borrado - RootController");

		return "redirect:/project/" + p.getName().replace(' ', '_');
	}

	@GetMapping("/bandeja")
	public String bandeja(HttpSession s, Model m) {
		User u = entityManager.find(User.class, ((User) s.getAttribute("user")).getId());

		logger.info("====== All Mail -- Nº Messages : " + u.getBandeja().size() + " ======");
		for (Correo c : u.getBandeja()) {
			logger.info("--- From: " + c.getAuthor().getName() + "--- To: " + c.getDestinatario().getName());
		}

		logger.info("====== Inbox -- Nº Messages : " + u.getInbox().size() + " ======");
		for (Correo c : u.getInbox()) {
			logger.info("--- From: " + c.getAuthor().getName() + "--- To: " + c.getDestinatario().getName());
		}

		logger.info("====== Outbox -- Nº Messages : " + u.getOutbox().size() + " ======");
		for (Correo c : u.getOutbox()) {
			logger.info("--- From: " + c.getAuthor().getName() + "--- To: " + c.getDestinatario().getName());
		}

		m.addAttribute("input", u.getInbox());
		m.addAttribute("output", u.getOutbox());

		return "bandeja";
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@Transactional
	public String sendMessage(@RequestParam String dest, @RequestParam String msg, HttpSession s) {

		User emisor = (User) s.getAttribute("user");
		emisor = entityManager.find(User.class, emisor.getId());

		User receptor = UserQueries.findWithName(entityManager, dest);

		if (receptor == null) {
			return "redirect:/";
		}

		receptor = entityManager.find(User.class, receptor.getId());

		Correo nuevo = new Correo();
		nuevo.setAuthor(emisor);
		nuevo.setDestinatario(receptor);
		nuevo.setMessage(Jsoup.parse(msg).text());

		entityManager.persist(nuevo);
		entityManager.flush();

		emisor.getBandeja().add(nuevo);
		receptor.getBandeja().add(nuevo);

		logger.info("====== New Message Created! ======");
		logger.info("From: " + nuevo.getAuthor().getName());
		logger.info("To: " + nuevo.getDestinatario().getName());
		logger.info("Message: " + nuevo.getMessage());

		return "redirect:/bandeja";
	}

}
