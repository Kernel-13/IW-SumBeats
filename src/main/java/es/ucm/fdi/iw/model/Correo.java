package es.ucm.fdi.iw.model;

import javax.persistence.*;

@Entity
public class Correo{
	
	private long id;
	private User author;
	private User destinatario;
	private String message;
	
	public Correo(){}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@ManyToOne(targetEntity=User.class)
	public User getAuthor() {
		return author;
	}

	@ManyToOne(targetEntity=User.class)
	public User getDestinatario() {
		return destinatario;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setDestinatario(User destinatario) {
		this.destinatario = destinatario;
	}
	
	@Override
	public boolean equals(Object obj){
		return obj!=null && obj.getClass() == this.getClass() && this.id == ((Correo)obj).id;
	}

}
