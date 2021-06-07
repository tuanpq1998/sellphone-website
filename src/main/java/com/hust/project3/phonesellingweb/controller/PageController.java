package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.project3.phonesellingweb.entity.Feedback;
import com.hust.project3.phonesellingweb.entity.User;
import com.hust.project3.phonesellingweb.service.FeedbackService;
import com.hust.project3.phonesellingweb.service.UserService;


@Controller 
public class PageController extends BaseController {

	@GetMapping({"/huong-dan-mua-hang/","/huong-dan-mua-hang" })
	public String showGuide() {
		return "guide";
	}
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public FeedbackService feedbackService;
	
	@GetMapping({"/lien-he/","/lien-he" })
	public String showContact(Model model, Principal principal) {
		Feedback feedback = new Feedback();
		if (isLoggedIn(principal)) {
			User user = userService.findByUsername(principal.getName());
			feedback.setFullname(user.getFullname());
			feedback.setEmail(user.getEmail());
		}
		
		model.addAttribute("feedback", feedback);
		return "contact";
	}
	
	@PostMapping({"/lien-he/","/lien-he" })
	public String handleFeedback(@ModelAttribute("feedback") Feedback feedback, Principal principal) {
		if (isLoggedIn(principal)) {
			User user = userService.findByUsername(principal.getName());
			feedback.setFullname(user.getFullname());
			feedback.setEmail(user.getEmail());
		}
		feedbackService.save(feedback);
		return "redirect:/lien-he?done";
	}
}
