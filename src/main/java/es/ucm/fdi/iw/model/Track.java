package es.ucm.fdi.iw.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
		@NamedQuery(name = "trackByName", query = "from Track t where t.name = :name")
})
public class Track {
	private long id;
	private String name;
	private User creator;
	private Proyecto project;

	public static final String ACTIVE = "active";
	public static final String PENDING = "pending";

	@Size(max = 100000)
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

	@ManyToOne(targetEntity=Proyecto.class)
	public Proyecto getProject() {
		return project;
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


	public void setProject(Proyecto project) {
		this.project = project;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj!=null && obj.getClass() == this.getClass() && this.id == ((Track)obj).id;
	}
}
