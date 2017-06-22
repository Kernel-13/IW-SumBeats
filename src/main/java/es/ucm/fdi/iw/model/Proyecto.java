package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Proyecto {
	private long id;
	private String name;
	
	@Size(max = 500)
	private String desc;
	
	private User author;
	private List<User> collaborators;
	private List<User> lovers;
	private List<Track> tracks;
	private List<Comentario> comments;
	private int weekRating;
	private int globalRating;
	private int icon;
	
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
	public List<Track> getTracks() {
		return tracks;
	}

	@Transient
	public List<Track> getCurrentTracks() {
		List<Track> filtered = new ArrayList<Track>();
		for (Track c : tracks) {
			if (c.getStatus().equals(Track.ACTIVE)) filtered.add(c);
		}
		return filtered;
	}

	@Transient
	public List<Track> getPendingTracks() {
		List<Track> filtered = new ArrayList<Track>();
		for (Track c : tracks) {
			if (c.getStatus().equals(Track.PENDING)) filtered.add(c);
		}
		return filtered;
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

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
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

	@ManyToMany(targetEntity=User.class, mappedBy="liked")
	public List<User> getLovers() {
		return lovers;
	}

	public void setLovers(List<User> lovers) {
		this.lovers = lovers;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
