package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.setting.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Integer> {

	public List<Slide> findByDisplayIsTrueOrderByIndexNumAsc();

}
