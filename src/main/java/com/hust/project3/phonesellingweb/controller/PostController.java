package com.hust.project3.phonesellingweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hust.project3.phonesellingweb.entity.Post;
import com.hust.project3.phonesellingweb.service.PostService;

@Controller
@RequestMapping("/bai-viet")
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping({"","/"})
	public String showAllPost() {
		
		return "post/category";
	}
	
	@GetMapping("/{postSlug}.{postId}.html")
	public String showPost(@PathVariable("postSlug") String postSlug, @PathVariable("postId") int postId
			, Model model) {
		Post post = postService.findById(postId);
		if (post == null)
			return "";
		String rootPostSlug = post.getSlug();
		if (rootPostSlug.equals(postSlug)) {
			model.addAttribute("post", post);
			
			return "post/post";	
		} else return "redirect:/bai-viet/"+rootPostSlug+"."+postId + ".html";
		
	}
}
