package com.hust.project3.phonesellingweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.ProductDao;
import com.hust.project3.phonesellingweb.entity.Color;
import com.hust.project3.phonesellingweb.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	public List<Product> getAll() {
		return productDao.findAll();
	}

	public Product findById(int productIdNumber) {
		Optional<Product> res = productDao.findById(productIdNumber);
		Product product = null;
		if (res.isPresent())
			product = res.get();
		return product;
	}
	
	
	public boolean isProductAndColorExistAndAvailable(Product product) {
		Product rootProduct = findById(product.getId());
		if (rootProduct != null) {
			if (rootProduct.isDeleted() || !rootProduct.isAvailable())
				return false;

			List<Color> colors = rootProduct.getColors();
			for (Color color : colors) {
				if (color.getId() == product.getColor().getId())
					return true;
			}
			
		}
		return false;
	}
	
	public int increaseSeenNum(int productId, int seenCount) {
		return productDao.increaseSeenNum(seenCount, productId);
	}

	public int increaseBuyCount(Product p) {
		return productDao.increaseBuyNum(p.getBuyCount()+1, p.getId());
	}
	
	public List<Product> findAllByManufacturerId(int manufacturerId) {
		return productDao.findByDeletedIsFalseAndManufacturer_Id(manufacturerId);
	}

	public List<Product> findAll() {
		return productDao.findByDeletedIsFalse();
	}

	public List<Product> findTop4Buy() {
		return productDao.findTop4ByDeletedIsFalseOrderByBuyCountDesc();
	}

	public List<Product> findTop6ByManufacturerId(int manufacturerId) {
		return productDao.findTop6ByDeletedIsFalseAndManufacturer_Id(manufacturerId);
	}
}
