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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hust.project3.phonesellingweb.utility.DateTimeHandler;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private int id;
	
	@JsonIgnore
	private int seenCount, buyCount;
	
	@Transient 
	private int quantity;
	
	@Transient 
	private double unitPrice;

	@Transient
	private Color color;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id + color.hashCode();
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
		Product other = (Product) obj;
		if (id != other.getId())
			return false;
		if (getColor() == null || !getColor().equals(other.getColor()))
			return false;
		return true;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name="name")
	private String name;
	
	@Column(name="ava_image")
	private String avaImage;
	
	@Column(name="updated_at")
	@JsonIgnore
	private String updatedAt;
	
	@Column(name="available")
	@JsonIgnore
	private boolean available;
	
	@Column(name="deleted")
	@JsonIgnore
	private boolean deleted;
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = DateTimeHandler.datetimeToString(new Date());
	}

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

	public String getAvaImage() {
		return avaImage;
	}

	public void setAvaImage(String avaImage) {
		this.avaImage = avaImage;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", quantity=" + quantity + ", color=" + color + ", price=" + price + "]";
	}

	public Product(int id, int seenCount, int buyCount, String name, String avaImage, String updatedAt,
			boolean available, boolean deleted) {
		super();
		this.id = id;
		this.seenCount = seenCount;
		this.buyCount = buyCount;
		this.name = name;
		this.avaImage = avaImage;
		this.updatedAt = updatedAt;
		this.available = available;
		this.deleted = deleted;
	}

	public Product() {
		super();
	}
	
	public Product(int id, int quantity, Color color) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.color = color;
	}

	public int getSeenCount() {
		return seenCount;
	}

	public void setSeenCount(int seenCount) {
		this.seenCount = seenCount;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	@OneToOne
	@JoinColumn(name="manufacturer_id")
	@JsonIgnore
	private Manufacturer manufacturer;
	
	@OneToOne
	@JoinColumn(name="price_id")
	@JsonIgnore
	private Price price;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonIgnore
	private List<Color> colors = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonIgnore
	private List<ProductImg> productImgs = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name="spec_id")
	private ProductSpec spec;

	public List<ProductImg> getProductImgs() {
		return productImgs;
	}

	public void setProductImgs(List<ProductImg> productImgs) {
		this.productImgs = productImgs;
	}

	public ProductSpec getSpec() {
		return spec;
	}

	public void setSpec(ProductSpec spec) {
		this.spec = spec;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	
}
