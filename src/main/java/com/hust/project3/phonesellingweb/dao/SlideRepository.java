package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.setting.Slide;

@Repository
public interface SlideRepository extends CrudRepository<Slide, Integer> {

	public List<Slide> findByShowIsTrueOrderByIndexAsc();

}
