package com.hust.project3.phonesellingweb.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.entity.Color;
import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.model.Cart;

@Service
public class CartService {

	@Autowired
	private ProductService productService;
	
	public void addProduct(Cart cart, Product product) {
		if (!productService.isProductAndColorExistAndAvailable(product))
			return;
		
		Product root = productService.findById(product.getId());
		
		root.setUnitPrice(root.getPrice().getValue());
		root.setQuantity(product.getQuantity());
		
		List<Color> rootColors = root.getColors();
		int indexColor = -1;
		for (int i = 0; i < rootColors.size(); i++) {
			if (rootColors.get(i).getId() == product.getColor().getId())
				indexColor = i;
		}
		root.setColor(rootColors.get(indexColor));
		
		
		cart.addProduct(root);
		
	}

	public void update(Cart rootCart, Cart userCart) {
		List<Product> products = userCart.getProducts();
		List<Product> rootProducts = rootCart.getProducts();

		for (int i = rootProducts.size() - 1; i >= 0; i--) {
			Product root = rootProducts.get(i);
			for (int j = products.size() - 1; j >= 0; j--) {
				Product p = products.get(j);
				if (p.getId() == root.getId() && p.getColor().getId() == root.getColor().getId()) {
					if (p.isDeleted())
						rootProducts.remove(i);
					else {
						root.setQuantity(p.getQuantity());
					}
				} 
			}
		}
		rootCart.updateTotalMoneyAndTotalQuantity();
	}
}
