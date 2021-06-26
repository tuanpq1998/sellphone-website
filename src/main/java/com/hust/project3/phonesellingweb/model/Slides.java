package com.hust.project3.phonesellingweb.model;

import java.util.ArrayList;
import java.util.List;

import com.hust.project3.phonesellingweb.entity.setting.Slide;

public class Slides {

	@Override
	public String toString() {
		return "Slides [listSlides=" + listSlides + "]";
	}

	private List<Slide> listSlides = new ArrayList<>();

	public List<Slide> getListSlides() {
		return listSlides;
	}

	public void setListSlides(List<Slide> listSlides) {
		this.listSlides = listSlides;
	}

	public Slides(List<Slide> listSlides) {
		super();
		this.listSlides = listSlides;
	}

	public Slides() {
		super();
	}
	
}
