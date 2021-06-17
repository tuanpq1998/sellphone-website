package com.hust.project3.phonesellingweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.ManufacturerDao;
import com.hust.project3.phonesellingweb.entity.Manufacturer;

@Service
public class ManufacturerService {

	@Autowired
	private ManufacturerDao manufacturerDao;
	
	@Autowired
	private ProductService productService;
	
	public Manufacturer findById(int id) {
		Optional<Manufacturer> result = manufacturerDao.findById(id);
		Manufacturer manufacturer = null;
		if (result.isPresent())
			manufacturer = result.get();
		return manufacturer;
	}
	
	public List<Manufacturer> findAll() {
		return manufacturerDao.findAll();
	}

	public Manufacturer save(Manufacturer manufacturer) {
		return manufacturerDao.save(manufacturer);
	}

	public void deleteById(int manufacturerId) {
		productService.moveToNoBrand(manufacturerId);
		manufacturerDao.deleteById(manufacturerId);
	}
}
