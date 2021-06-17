package com.hust.project3.phonesellingweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="price")
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="price_id")
	private int priceId;
	
	@Column(name="value")
	private double value;
	
	@Column(name="start_date")
	private String startDate;
	
	@Column(name="end_date")
	private String endDate;
	
	private boolean current;
	
	@OneToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product product;

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Price(int priceId, double value, String startDate, String endDate) {
		super();
		this.priceId = priceId;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Price() {
		super();
	}

	@Override
	public String toString() {
		return "Price [priceId=" + priceId + ", value=" + value + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}

	public int getPriceId() {
		return priceId;
	}

	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
