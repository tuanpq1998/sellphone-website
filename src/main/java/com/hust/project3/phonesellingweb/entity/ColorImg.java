package com.hust.project3.phonesellingweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="color_img")
public class ColorImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="colorimg_id")
	private int colorImgId;
	
	private String url;

	public int getColorImgId() {
		return colorImgId;
	}

	public void setColorImgId(int colorImgId) {
		this.colorImgId = colorImgId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="color_id")
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "ColorImg [colorImgId=" + colorImgId + ", url=" + url + "]";
	}
	
}
