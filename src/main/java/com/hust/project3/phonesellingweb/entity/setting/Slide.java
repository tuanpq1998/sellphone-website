package com.hust.project3.phonesellingweb.entity.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="slide")
public class Slide {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	private String imageUrl;
	
	private String anchorUrl;
	
	private String title;
	
	private int index;
	
	private boolean show;

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

	@Override
	public String toString() {
		return "Slide [id=" + id + ", imageUrl=" + imageUrl + ", anchorUrl=" + anchorUrl + ", title=" + title + "]";
	}

	public Slide() {
		super();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Slide(int id, String imageUrl, String anchorUrl, String title, int index, boolean show) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.anchorUrl = anchorUrl;
		this.title = title;
		this.index = index;
		this.show = show;
	}
	
	
}
