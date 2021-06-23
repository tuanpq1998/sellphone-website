package com.hust.project3.phonesellingweb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.hust.project3.phonesellingweb.utility.DateTimeHandler;

@Entity
@Table(name="bill")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bill",  cascade = CascadeType.ALL)
	private List<BillDetail> billDetails = new ArrayList<>();
	
	public List<BillDetail> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}

	private double totalMoney;
	
	public Bill() {
		super();
	}

	public Bill(int id, double totalMoney, String createAt, int totalQuantity, String status, String note,
			String customAddress, String customPhone, boolean done) {
		super();
		this.id = id;
		this.totalMoney = totalMoney;
		this.createAt = createAt;
		this.totalQuantity = totalQuantity;
		this.status = status;
		this.note = note;
		this.customAddress = customAddress;
		this.customPhone = customPhone;
		this.done = done;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", totalMoney=" + totalMoney + ", createAt=" + createAt + ", totalQuantity="
				+ totalQuantity + ", status=" + status + ", note=" + note + ", customAddress=" + customAddress
				+ ", customPhone=" + customPhone + ", done=" + done + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCustomAddress() {
		return customAddress;
	}

	public void setCustomAddress(String customAddress) {
		this.customAddress = customAddress;
	}

	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Column(updatable = false)
	private String createAt;
	
	private int totalQuantity;
	
	private String status, note, customAddress, customPhone;
	
	private boolean done;
	
	@PrePersist
	protected void onCreate() {
		createAt = DateTimeHandler.datetimeToString(new Date());
	}
	
}
