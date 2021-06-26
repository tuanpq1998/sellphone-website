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
	
	private int indexNum;
	
	private boolean display;

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

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public Slide(int id, String imageUrl, String anchorUrl, String title, int indexNum, boolean display) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.anchorUrl = anchorUrl;
		this.title = title;
		this.indexNum = indexNum;
		this.display = display;
	}

	public Slide() {
		super();
	}

	@Override
	public String toString() {
		return "Slide [id=" + id + ", imageUrl=" + imageUrl + ", anchorUrl=" + anchorUrl + ", title=" + title
				+ ", indexNum=" + indexNum + ", display=" + display + "]";
	}

}
