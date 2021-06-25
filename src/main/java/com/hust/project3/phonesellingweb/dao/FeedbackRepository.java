package com.hust.project3.phonesellingweb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Feedback;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

	public Page<Feedback> findByOrderByCreateAtDesc(Pageable pageable);

	public List<Feedback> findTop3ByOrderByCreateAtDesc();

}
