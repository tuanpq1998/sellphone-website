package com.hust.project3.phonesellingweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.BillRepository;
import com.hust.project3.phonesellingweb.entity.Bill;
import com.hust.project3.phonesellingweb.entity.BillDetail;
import com.hust.project3.phonesellingweb.entity.User;
import com.hust.project3.phonesellingweb.entity.product.Product;
import com.hust.project3.phonesellingweb.model.Cart;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private ProductService productService;

	public Bill createAndSave(User user, Cart cart) {
		Bill bill = new Bill();
		bill.setStatus("Chưa xác nhận");
		bill.setUser(user);
		bill.setTotalMoney(cart.getTotalMoney());
		bill.setTotalQuantity(cart.getTotalQuantity());
		
		if (cart.getAddress() != null && !cart.getAddress().equals(""))
			bill.setCustomAddress(cart.getAddress());
		else bill.setCustomAddress(user.getAddress());
		if (cart.getPhone() != null && !cart.getPhone().equals(""))
			bill.setCustomPhone(cart.getPhone());
		else bill.setCustomPhone(user.getPhone());
		
		List<Product> products = cart.getProducts();
		List<BillDetail> billDetails = new ArrayList<>();
				
		for(Product p : products) {
			BillDetail billDetail = new BillDetail();
			billDetail.setProduct(p);
			billDetail.setColorname(p.getColor().getName());
			billDetail.setUnitPrice(p.getUnitPrice());
			billDetail.setQuantity(p.getQuantity());
			billDetail.setSubTotal(p.getUnitPrice() * p.getQuantity());
			
			billDetail.setBill(bill);
			billDetails.add(billDetail);
			
			productService.increaseBuyCount(p);
		}
		
		bill.setBillDetails(billDetails);
		
		return billRepository.save(bill);
	}
	
	public List<Bill> findTop5ByUserId(int userId) {
		return billRepository.findTop5ByUser_IdOrderByIdDesc(userId);
	}

	public Page<Bill> findAll(int numperpage, Integer page) {
		Pageable pageable = PageRequest.of(page-1, numperpage);
		return billRepository.findByOrderByCreateAtDesc(pageable);
	}

	public Bill findById(int billId) {
		Optional<Bill> result = billRepository.findById(billId);
		Bill b = null;
		if (result.isPresent())
			b = result.get();
		return b;
	}

	public void update(Bill bill) {
		billRepository.save(bill);
	}

	public Page<Bill> findAllLike(int numperpage, Integer page, int id) {
		Pageable pageable = PageRequest.of(page-1, numperpage);
		return billRepository.findByIdOrderByCreateAtDesc(id, pageable);
	}

	public int countAll() {
		return (int) billRepository.count();
	}
	
}
