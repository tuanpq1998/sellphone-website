package com.hust.project3.phonesellingweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.FeedbackRepository;
import com.hust.project3.phonesellingweb.entity.Feedback;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	public Feedback save(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}
}
