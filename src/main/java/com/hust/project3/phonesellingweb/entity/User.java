package com.hust.project3.phonesellingweb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hust.project3.phonesellingweb.utility.DateTimeHandler;
import com.hust.project3.phonesellingweb.validation.UniqueUsername;
import com.hust.project3.phonesellingweb.validation.ValidationGroup.OnLogin;
import com.hust.project3.phonesellingweb.validation.ValidationGroup.OnRegister;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank(message = "Nhập tên đăng nhập!", groups = {OnLogin.class, OnRegister.class, Default.class})
	@UniqueUsername(message = "Tên đăng nhập đã được đăng kí!", groups = {OnRegister.class, Default.class})
	private String username;
	
	@JsonIgnore
	@NotBlank(message = "Nhập mật khẩu!",  groups = {OnLogin.class, OnRegister.class, Default.class})
	@Size(min=6, message="Mật khẩu phải chứa hơn 6 kí tự!",  groups = {OnLogin.class, OnRegister.class, Default.class})
	private String password;
	
	@Email(message = "Email không hợp lệ!",  groups = {OnRegister.class, Default.class})
	@NotBlank(message = "Nhập email!")
	private String email;
	
	@NotBlank(message = "Nhập tên đầy đủ!", groups = {OnRegister.class, Default.class})
	private String fullname;
	
	@Pattern(regexp="^[0-9]{8,}$",
			message="Số điện thoại chỉ gồm số!", groups = {OnRegister.class, Default.class})
	private String phone;
	
	@Column(updatable = false)
	private String joinDate;
	
	private boolean disable;
	
	private String address;
	
	private String city;
	
	public User(int id, String username, String password, String email, String fullname, String phone, String joinDate,
			boolean disable, String address, String city) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.phone = phone;
		this.joinDate = joinDate;
		this.disable = disable;
		this.address = address;
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", fullname=" + fullname + ", phone=" + phone + ", joinDate=" + joinDate + ", disable=" + disable
				+ ", address=" + address + ", city=" + city + "]";
	}

	public User() {
		super();
	}
	
	@PrePersist
	protected void onCreate() {
		joinDate = DateTimeHandler.dateToString(new Date());
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnore
	private List<Bill> bills = new ArrayList<Bill>();

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
