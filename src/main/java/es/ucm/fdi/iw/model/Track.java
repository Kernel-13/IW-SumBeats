package es.ucm.fdi.iw.model;

import javax.persistence.*;

@Entity
public class Track {
	private long id;
	private User creator;
	private String instrumento;
	private String status; // PREGUNTAR 
	// private String path;
	// private MIDItrack midi;
	// More MIDI stuff
	
	public Track(){}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@ManyToOne(targetEntity=User.class)
	public User getCreator() {
		return creator;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
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
