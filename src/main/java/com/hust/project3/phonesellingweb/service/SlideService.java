package com.hust.project3.phonesellingweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.SlideRepository;
import com.hust.project3.phonesellingweb.entity.setting.Slide;
import com.hust.project3.phonesellingweb.model.Slides;

@Service
public class SlideService {

	@Autowired
	private SlideRepository slideRepository;
	
	public List<Slide> findAll() {
		return slideRepository.findByDisplayIsTrueOrderByIndexNumAsc();
	}

	public void save(Slides slides) {
		List<Slide> ls = slides.getListSlides();
		for (int i = 0; i < ls.size(); i++) {
			Slide s = ls.get(i);
			if (s.getId() == 0) {
				s.setDisplay(true);
				s.setIndexNum(i+1);
				ls.set(i, s);
			}
		}
		slideRepository.saveAll(ls);
	}
}
