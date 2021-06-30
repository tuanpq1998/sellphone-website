package com.hust.project3.phonesellingweb.entity.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="banner")
public class Banner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	private String imageUrl;
	
	private String anchorUrl;
	
	private String title;
	
	private String position;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAnchorUrl() {
		return anchorUrl;
	}

	public void setAnchorUrl(String anchorUrl) {
		this.anchorUrl = anchorUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Banner [id=" + id + ", imageUrl=" + imageUrl + ", anchorUrl=" + anchorUrl + ", title=" + title
				+ ", position=" + position + "]";
	}

	public Banner() {
		super();
	}

	public Banner(int id, String imageUrl, String anchorUrl, String title, String position) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.anchorUrl = anchorUrl;
		this.title = title;
		this.position = position;
	}

}
