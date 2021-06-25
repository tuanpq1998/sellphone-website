package com.hust.project3.phonesellingweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.SlideRepository;
import com.hust.project3.phonesellingweb.entity.setting.Slide;

@Service
public class SlideService {

	@Autowired
	private SlideRepository slideRepository;
	
	public List<Slide> findAll() {
		return slideRepository.findByShowIsTrueOrderByIndexAsc();
	}
}
