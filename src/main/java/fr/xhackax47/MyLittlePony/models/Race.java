package fr.xhackax47.MyLittlePony.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="RACE")
public class Race implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="LOCATION")
	private String location;
	
	@Column(name="DATE")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@ManyToMany
	@JoinTable(name="race_pony", joinColumns=@JoinColumn(name="race_id"), inverseJoinColumns=@JoinColumn(name="pony_id"))	
	private Collection<Pony> ponies;
	
	public Race() {}
	
	public Race(String location, Date date) {
		super();
		this.location = location;
		this.date = date;
	}
	
	public Race(Long id, String location, Date date, Collection<Pony> ponies) {
		super();
		this.id = id;
		this.location = location;
		this.date = date;
		this.ponies = ponies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Pony> getPonies() {
		return ponies;
	}

	public void setPonies(Collection<Pony> collection) {
		this.ponies = collection;
	}

}