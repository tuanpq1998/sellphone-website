package com.hust.project3.phonesellingweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manufacturer")
public class Manufacturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="manufacturer_id")
	private int id;
	
	private String name;
	
	private String image;
	
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Manufacturer [id=" + id + ", name=" + name + ", image=" + image + ", country=" + country + "]";
	}

	public Manufacturer(int id, String name, String image, String country) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.country = country;
	}

	public Manufacturer() {
		super();
	}
	
	
}
