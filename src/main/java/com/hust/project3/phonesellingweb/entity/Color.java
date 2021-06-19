package com.hust.project3.phonesellingweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="color")
public class Color {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="color_id")
	private int id;
	
	private String name, hexCode;
		
	public Color(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Color() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getHexCode() {
		return hexCode;
	}

	public void setHexCode(String hexCode) {
		this.hexCode = hexCode;
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
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@JsonIgnore
	private Product product;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "color")
	@JsonIgnore
	private List<ColorImg> colorImgs = new ArrayList<>();
	
	@Override
	public String toString() {
		return "Color [id=" + id + ", name=" + name + ", hexCode=" + hexCode + ", product=" + product + ", colorImgs="
				+ colorImgs + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Color(int id) {
		super();
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public Color(int id, String name, String hexCode) {
		super();
		this.id = id;
		this.name = name;
		this.hexCode = hexCode;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ColorImg> getColorImgs() {
		return colorImgs;
	}

	public void setColorImgs(List<ColorImg> colorImgs) {
		this.colorImgs = colorImgs;
	}

	
}
