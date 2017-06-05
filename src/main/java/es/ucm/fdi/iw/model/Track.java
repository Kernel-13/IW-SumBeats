package es.ucm.fdi.iw.model;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "trackByName", query = "from Track t where t.name = :name")
})
public class Track {
	private long id;
	private String name;
	private User creator;
	private String abc;
	private String status;
	
	public Track(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@ManyToOne(targetEntity=User.class)
	public User getCreator() {
		return creator;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}
