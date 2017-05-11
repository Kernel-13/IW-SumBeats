package es.ucm.fdi.iw.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class User {
	
	private long id;
	private String name;
	private String password; // TEMPORAL
	private String desc;
	private String email;
	private List<Proyecto> projects;
	private List<Proyecto> collaborations;
	private List<User> friends;
	private Date dateJoined;
	private Date lastConnected;
	private List<Correo> bandeja;
	private String roles;
	
	public User(){}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@OneToMany(targetEntity=Proyecto.class)
	@JoinColumn(name="author")
	public List<Proyecto> getProjects() {
		return projects;
	}

	@ManyToMany(targetEntity=User.class) //MappedBy ... ?
	public List<User> getFriends() {
		return friends;
	}
	
	@OneToMany(targetEntity=Correo.class)
	@JoinColumn(name="author")
	public List<Correo> getBandeja() {
		return bandeja;
	}
	
	@ManyToMany(targetEntity=Proyecto.class)
	public List<Proyecto> getCollaborations() {
		return collaborations;
	}
	
	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Date getLastConnected() {
		return lastConnected;
	}

	public void setLastConnected(Date lastConnected) {
		this.lastConnected = lastConnected;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProjects(List<Proyecto> projects) {
		this.projects = projects;
	}

	public void setCollaborations(List<Proyecto> collaborations) {
		this.collaborations = collaborations;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public void setBandeja(List<Correo> bandeja) {
		this.bandeja = bandeja;
	}

}
