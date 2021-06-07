package com.hust.project3.phonesellingweb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.hust.project3.phonesellingweb.utility.DateTimeHandler;

@Entity
@Table(name="feedback")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	private String fullname, email, title, detail;
	
	@Column(name="create_at", updatable = false)
	private String createAt;
	
	@PrePersist
	protected void onCreate() {
		createAt = DateTimeHandler.datetimeToString(new Date());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", fullname=" + fullname + ", email=" + email + ", title=" + title + ", detail="
				+ detail + ", createAt=" + createAt + "]";
	}

	public Feedback(int id, String fullname, String email, String title, String detail, String createAt) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.title = title;
		this.detail = detail;
		this.createAt = createAt;
	}

	public Feedback() {
		super();
	}
		
}
