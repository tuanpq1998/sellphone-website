package com.hust.project3.phonesellingweb.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hust.project3.phonesellingweb.entity.Feedback;
import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.entity.User;
import com.hust.project3.phonesellingweb.entity.product.Product;
import com.hust.project3.phonesellingweb.entity.setting.Slide;
import com.hust.project3.phonesellingweb.service.FeedbackService;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.PostService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.service.SlideService;
import com.hust.project3.phonesellingweb.service.UserService;


@Controller 
public class PageController extends BaseController {
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private SlideService slideService;
	
	@GetMapping({"/","" })
	public String showIndex(Model model) {
		List<Slide> slides = slideService.findAll(); 
		
		List<Product> topBuyProducts = productService.findTop4Buy();
		List<Product> samsungProducts = productService.findTop6ByManufacturerId(1);
		List<Product> appleProducts = productService.findTop6ByManufacturerId(2);
		Manufacturer samsung = manufacturerService.findById(1);
		Manufacturer apple = manufacturerService.findById(2);
		
		model.addAttribute("topBuyProducts", topBuyProducts);
		
		model.addAttribute("samsungProducts", samsungProducts);
		model.addAttribute("samsung", samsung);
		
		model.addAttribute("appleProducts", appleProducts);
		model.addAttribute("apple", apple);
		
		model.addAttribute("posts", postService.findNewest(4));
		
		model.addAttribute("slides", slides);
		model.addAttribute("isHomepage", true);
		return "index";
	}
	
	@GetMapping({"/huong-dan-mua-hang/","/huong-dan-mua-hang" })
	public String showGuide() {
		return "guide";
	}
	
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
