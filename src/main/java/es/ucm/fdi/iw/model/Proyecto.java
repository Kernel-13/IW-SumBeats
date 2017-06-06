package es.ucm.fdi.iw.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Proyecto {
	private long id;
	private String name;
	private String desc;
	private User author;
	private List<User> collaborators;
	private List<Track> currentTracks;
	private List<Track> pendingTracks;
	private List<Comentario> comments;
	private int weekRating;
	private int globalRating;
	
	public Proyecto(){}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@ManyToOne(targetEntity=User.class)
	public User getAuthor() {
		return author;
	}
	
	@ManyToMany(targetEntity=User.class, mappedBy="collaborations")
	public List<User> getCollaborators() {
		return collaborators;
	}

	@OneToMany(targetEntity=Track.class)
	@JoinColumn(name="proyecto")
	public List<Track> getCurrentTracks() {
		return currentTracks;
	}

	@OneToMany(targetEntity=Track.class)
	@JoinColumn(name="proyecto")
	public List<Track> getPendingTracks() {
		return pendingTracks;
	}
	
	@OneToMany(targetEntity=Comentario.class)
	@JoinColumn(name="proyecto") 
	public List<Comentario> getComments() {
		return comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getWeekRating() {
		return weekRating;
	}

	public void setWeekRating(int weekRating) {
		this.weekRating = weekRating;
	}

	public int getGlobalRating() {
		return globalRating;
	}

	public void setGlobalRating(int globalRating) {
		this.globalRating = globalRating;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setCollaborators(List<User> collaborators) {
		this.collaborators = collaborators;
	}

	public void setCurrentTracks(List<Track> currentTracks) {
		this.currentTracks = currentTracks;
	}

	public void setPendingTracks(List<Track> pendingTracks) {
		this.pendingTracks = pendingTracks;
	}

	public void setComments(List<Comentario> comments) {
		this.comments = comments;
	}
	public boolean isCollaborator(User co){
//		if(collaborators.isEmpty()){
//			return false;
		//REDUNDANTE
		return collaborators.contains(co);
		
		
	}
}
