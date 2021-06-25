package com.hust.project3.phonesellingweb.model;

import java.util.ArrayList;
import java.util.List;

import com.hust.project3.phonesellingweb.entity.product.Product;

public class Cart {
	
	@Override
	public String toString() {
		return "Cart [products=" + products + ", totalMoney=" + totalMoney + ", totalQuantity=" + totalQuantity + "]";
	}

	private List<Product> products = new ArrayList<>();
	
	private double totalMoney;
	
	private int totalQuantity;

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Cart() {
		super();
	}
	
	public void updateTotalMoneyAndTotalQuantity() {
		double totalM = 0;
		int totalQ = 0;
		for (Product product : products) { 
			int temp = product.getQuantity();
			totalQ += temp;
			totalM += temp * product.getUnitPrice();
		}
		setTotalMoney(totalM);
		setTotalQuantity(totalQ);
	}
	
	public int indexOf(Product product) {
		int res = -1, colorId = product.getColor().getId();
		if (products == null) return res;
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			if (p.getId() == product.getId() && p.getColor().getId() == colorId)
				return i;
		} 
		return res;
	}
	
	public void addProduct(Product product) {
		
		
		int index = indexOf(product);
		if (index == -1)
			products.add(product);
		else {
			Product currentProduct = products.get(index);
			currentProduct.setQuantity(currentProduct.getQuantity() + product.getQuantity());
			products.set(index, currentProduct);
		}
		
		updateTotalMoneyAndTotalQuantity();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalMoney);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + totalQuantity;
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
		Cart other = (Cart) obj;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (Double.doubleToLongBits(totalMoney) != Double.doubleToLongBits(other.totalMoney))
			return false;
		if (totalQuantity != other.totalQuantity)
			return false;
		return true;
	}

	private String address, phone;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
