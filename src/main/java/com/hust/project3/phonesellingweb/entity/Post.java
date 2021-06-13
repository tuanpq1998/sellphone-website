package com.hust.project3.phonesellingweb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.hust.project3.phonesellingweb.utility.DateTimeHandler;

@Entity
@Table(name="post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	private String title, avaImage, slug, body, bannerImage;
	

	private boolean draft;
	
	private int viewCount;
	
	@Column(updatable = false)
	private String createAt;
	
	private String updateAt;
	
	private String description;
	
	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@PrePersist
	protected void onCreate() {
		createAt = DateTimeHandler.datetimeToString(new Date());
	}
	
	@PreUpdate
	protected void onUpdate() {
		updateAt = DateTimeHandler.datetimeToString(new Date());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAvaImage() {
		return avaImage;
	}

	public void setAvaImage(String avaImage) {
		this.avaImage = avaImage;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", avaImage=" + avaImage + ", slug=" + slug + ", body=" + body
				+ ", draft=" + draft + ", viewCount=" + viewCount + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", description=" + description + "]";
	}

	public Post(int id, String title, String avaImage, String slug, String body, boolean draft, int viewCount,
			String createAt, String updateAt) {
		super();
		this.id = id;
		this.title = title;
		this.avaImage = avaImage;
		this.slug = slug;
		this.body = body;
		this.draft = draft;
		this.viewCount = viewCount;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Post() {
		super();
	}
	
	
}
