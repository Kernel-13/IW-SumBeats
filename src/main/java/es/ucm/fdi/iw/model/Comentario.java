package es.ucm.fdi.iw.model;

import javax.persistence.*;

@Entity
public class Comentario{
	
	private long id;
	private User autor;
	private Proyecto proyecto;
	private String message;
	private boolean hidden;
	
	public Comentario(){}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(targetEntity=User.class)
	public User getAutor() {
		return autor;
	}

	@ManyToOne(targetEntity=Proyecto.class)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
}
