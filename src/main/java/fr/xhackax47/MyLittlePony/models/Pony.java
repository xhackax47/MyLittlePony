package fr.xhackax47.MyLittlePony.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="PONY")
public class Pony implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="WEIGHT")
	private Integer weight;
	
	@Column(name="AGE")
	private Integer age;
	
	@Column(name="COLOR")
	private String color;
	
	
	public Pony() {}
	
	public Pony(String name, Integer weight, Integer age, String color) {
		super();
		this.name = name;
		this.weight = weight;
		this.age = age;
		this.color = color;
	}
	
	public Pony(Long id, String name, Integer weight, Integer age, String color) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.age = age;
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
