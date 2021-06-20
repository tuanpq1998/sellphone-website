package com.hust.project3.phonesellingweb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.ProductDao;
import com.hust.project3.phonesellingweb.entity.Color;
import com.hust.project3.phonesellingweb.entity.ColorImg;
import com.hust.project3.phonesellingweb.entity.Price;
import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.entity.ProductImg;
import com.hust.project3.phonesellingweb.utility.ConstantVariable;
import com.hust.project3.phonesellingweb.utility.StringHandler;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	// @Value("${com.tuanpq.myaskfm.qperpage}")
	private int numProductPerPage = 9;
	
	private int numRandomProduct = 6;
	
	public int countAll() {
		return productDao.countByDeletedIsFalse();
	}

	public List<Product> getAll() {
		return productDao.findAll();
	}
	
	public List<Product> getRandomProducts() {
		int total = countAll();
		int totalPages = (total % numRandomProduct == 0) ? (total / numRandomProduct) : (total / numRandomProduct)+1;
		int pageIndex = (int) (Math.random()*totalPages);
		PageRequest request = PageRequest.of(pageIndex, numRandomProduct);
		Page<Product> page = productDao.findByDeletedIsFalse(request);
		List<Product> result = new ArrayList<Product>(page.getContent());
		Collections.shuffle(result);
		return result;
	}

	public Product findById(int productIdNumber) {
		Optional<Product> res = productDao.findByIdAndDeletedIsFalse(productIdNumber);
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
		return productDao.increaseBuyNum(p.getBuyCount() + 1, p.getId());
	}

	public Page<Product> findAllByManufacturerId(int manufacturerId, int page, String sortType) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (sortType == null)
			return productDao.findByDeletedIsFalseAndManufacturer_Id(manufacturerId, pageable);
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC))
			return productDao.findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueAsc(manufacturerId, pageable);
		else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC))
			return productDao.findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueDesc(manufacturerId, pageable);
		return productDao.findByDeletedIsFalseAndManufacturer_Id(manufacturerId, pageable);
	}

	public Page<Product> findAll(int page, String sortType) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (sortType == null)
			return productDao.findByDeletedIsFalse(pageable);
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC))
			return productDao.findByDeletedIsFalseOrderByPrice_ValueAsc(pageable);
		else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC))
			return productDao.findByDeletedIsFalseOrderByPrice_ValueDesc(pageable);
		return productDao.findByDeletedIsFalse(pageable);
	}

	public Page<Product> findAll(int page, String sortType, String priceRange) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (StringHandler.isEmpty(sortType)) {

			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalse(pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueLessThan(range[1], pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueGreaterThan(range[0], pageable);
				return productDao.findByDeletedIsFalseAndPrice_ValueBetween(range[0], range[1], pageable);
			}

		}
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseOrderByPrice_ValueAsc(pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueLessThanOrderByPrice_ValueAsc(range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(range[0],
							pageable);
				return productDao.findByDeletedIsFalseAndPrice_ValueBetweenOrderByPrice_ValueAsc(range[0], range[1],
						pageable);
			}
		} else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseOrderByPrice_ValueDesc(pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueLessThanOrderByPrice_ValueDesc(range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(range[0],
							pageable);
				return productDao.findByDeletedIsFalseAndPrice_ValueBetweenOrderByPrice_ValueDesc(range[0], range[1],
						pageable);
			}
		}
		return productDao.findByDeletedIsFalse(pageable);
	}

	public List<Product> findTop4Buy() {
		return productDao.findTop4ByDeletedIsFalseOrderByBuyCountDesc();
	}

	public List<Product> findTop6ByManufacturerId(int manufacturerId) {
		return productDao.findTop6ByDeletedIsFalseAndManufacturer_Id(manufacturerId);
	}

	public Page<Product> findByManufacturerIdAndNameLike(int manufacturerId, Integer page, String sortType,
			String searchKey) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (sortType == null)
			return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContaining(manufacturerId, pageable,
					searchKey);
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC))
			return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueAsc(
					manufacturerId, pageable, searchKey);
		else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC))
			return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueDesc(
					manufacturerId, pageable, searchKey);
		return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContaining(manufacturerId, pageable, searchKey);
	}

	public Page<Product> findByNameLike(int page, String sortType, String searchKey) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (sortType == null)
			return productDao.findByDeletedIsFalseAndNameContaining(pageable, searchKey);
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC))
			return productDao.findByDeletedIsFalseAndNameContainingOrderByPrice_ValueAsc(pageable, searchKey);
		else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC))
			return productDao.findByDeletedIsFalseAndNameContainingOrderByPrice_ValueDesc(pageable, searchKey);
		return productDao.findByDeletedIsFalseAndNameContaining(pageable, searchKey);
	}

	public Page<Product> findByNameLike(Integer page, String sortType, String searchKey, String priceRange) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (StringHandler.isEmpty(sortType)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndNameContaining(pageable, searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThan(searchKey, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThan(searchKey,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueBetween(searchKey, range[0],
						range[1], pageable);
			}
		}

		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC)) {

			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndNameContainingOrderByPrice_ValueAsc(pageable, searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueAsc(
							searchKey, range[1], pageable);
				if (range[1] == 0)
					return productDao
							.findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(
									searchKey, range[0], pageable);
				return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueAsc(
						searchKey, range[0], range[1], pageable);
			}
		}

		else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndNameContainingOrderByPrice_ValueDesc(pageable, searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueDesc(
							searchKey, range[1], pageable);
				if (range[1] == 0)
					return productDao
							.findByDeletedIsFalseAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(
									searchKey, range[0], pageable);
				return productDao.findByDeletedIsFalseAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueDesc(
						searchKey, range[0], range[1], pageable);
			}
		}

		return productDao.findByDeletedIsFalseAndNameContaining(pageable, searchKey);
	}

	public Page<Product> findAllByManufacturerId(int manufacturerId, Integer page, String sortType, String priceRange) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (StringHandler.isEmpty(sortType)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_Id(manufacturerId, pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThan(manufacturerId, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThan(manufacturerId,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetween(manufacturerId, range[0],
						range[1], pageable);
			}

		}
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueAsc(manufacturerId, pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThanOrderByPrice_ValueAsc(manufacturerId, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(manufacturerId,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetweenOrderByPrice_ValueAsc(manufacturerId, range[0],
						range[1], pageable);
			}
			
		} else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_IdOrderByPrice_ValueDesc(manufacturerId, pageable);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueLessThanOrderByPrice_ValueDesc(manufacturerId, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(manufacturerId,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndPrice_ValueBetweenOrderByPrice_ValueDesc(manufacturerId, range[0],
						range[1], pageable);
			}
			
		}
			
		return productDao.findByDeletedIsFalseAndManufacturer_Id(manufacturerId, pageable);
	}

	public Page<Product> findByManufacturerIdAndNameLike(int manufacturerId, Integer page, String sortType, String searchKey,
			String priceRange) {
		Pageable pageable = PageRequest.of(page - 1, numProductPerPage);
		if (StringHandler.isEmpty(sortType)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContaining(manufacturerId, pageable,
						searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThan(manufacturerId, searchKey, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThan(manufacturerId, searchKey,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetween(manufacturerId, searchKey, range[0],
						range[1], pageable);
			}

		}
			
		if (sortType.equals(ConstantVariable.SORT_PRICE_ASC)) {
			
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueAsc(
						manufacturerId, pageable, searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueAsc(manufacturerId, searchKey, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueAsc(manufacturerId, searchKey,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueAsc(manufacturerId, searchKey, range[0],
						range[1], pageable);
			}

		} else if (sortType.equals(ConstantVariable.SORT_PRICE_DESC)) {
			if (StringHandler.isEmpty(priceRange))
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingOrderByPrice_ValueDesc(
						manufacturerId, pageable, searchKey);
			else {
				double range[] = StringHandler.toPriceRangeValue(priceRange);
				if (range[0] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueLessThanOrderByPrice_ValueDesc(manufacturerId, searchKey, range[1],
							pageable);
				if (range[1] == 0)
					return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueGreaterThanOrderByPrice_ValueDesc(manufacturerId, searchKey,
							range[0], pageable);
				return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContainingAndPrice_ValueBetweenOrderByPrice_ValueDesc(manufacturerId, searchKey, range[0],
						range[1], pageable);
			}
			

		}
		return productDao.findByDeletedIsFalseAndManufacturer_IdAndNameContaining(manufacturerId, pageable, searchKey);
	}

	public Page<Product> findAllForAdmin(int numPerPage, int page) {
		Pageable pageable = PageRequest.of(page - 1, numPerPage, Sort.by("createAt").descending());
			return productDao.findAll(pageable);
	}

	public Page<Product> findAllForAdminLike(String searchKey, int numPerPage, int page) {
		Pageable pageable = PageRequest.of(page - 1, numPerPage);
		return productDao.findByNameContaining(pageable, searchKey);
	}
	
	public int moveToNoBrand(int manufacturerId) {
		return productDao.updateManufacturerIdToNoBrand(manufacturerId);
	}

	public void deleteById(int productId) {
		productDao.updateDeleteById(productId);
	}

	public void save(Product product) {
		List<Color> colors = product.getColors();
		for (Color c : colors) {
			c.setProduct(product);
			
			List<ColorImg> imgs = c.getColorImgs();
			for (ColorImg img : imgs)
				img.setColor(c);
		}
		
		Price p = product.getPrice();
		p.setCurrent(true);
		p.setProduct(product);
		
		List<ProductImg> imgs = product.getProductImgs();
		for (ProductImg img : imgs) 
			img.setProduct(product);
		
		
		productDao.save(product);
	}

}
