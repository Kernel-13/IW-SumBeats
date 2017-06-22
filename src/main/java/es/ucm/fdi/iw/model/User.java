package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {
	
	private long id;
	private String name;
	private String password; // TEMPORAL
	
	@Size(max = 500)
	private String description;
	
	private String email;
	private List<Proyecto> projects;
	private List<Proyecto> collaborations;
	private List<Proyecto> liked;
	
	private List<User> friends;
	private Date dateJoined;
	private Date lastConnected;
	private List<Correo> bandeja;
	private String roles;
	private List<Track> tracks;
	private int icon;
	
	public User(){}
	
	@OneToMany(targetEntity=Track.class)
	@JoinColumn(name="creator")
	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public boolean equals(Object obj) {
		return obj!=null && obj.getClass() == this.getClass() && this.id == ((User)obj).id;
	}
	
	@Transient
	public List<Correo> getOutbox() {
		List<Correo> filtered = new ArrayList<Correo>();
		for (Correo c : bandeja) {
			if (c.getAuthor().getId() == this.id) 
				filtered.add(c);
		}
		return filtered;
	}

	@Transient
	public List<Correo> getInbox() {
		List<Correo> filtered = new ArrayList<Correo>();
		for (Correo c : bandeja) {
			if (c.getDestinatario().getId() == this.id) 
				filtered.add(c);
		}
		return filtered;
	}


	@ManyToMany(targetEntity=Proyecto.class)
	public List<Proyecto> getLiked() {
		return liked;
	}

	public void setLiked(List<Proyecto> liked) {
		this.liked = liked;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	
}
