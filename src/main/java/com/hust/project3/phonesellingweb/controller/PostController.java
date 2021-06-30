package com.hust.project3.phonesellingweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hust.project3.phonesellingweb.entity.Post;
import com.hust.project3.phonesellingweb.service.BannerService;
import com.hust.project3.phonesellingweb.service.PostService;

@Controller
@RequestMapping("/bai-viet")
public class PostController extends BaseController {

	@Autowired
	private PostService postService;
	
	@GetMapping({"","/"})
	public String showAllPost(Model model) {
		
		model.addAttribute("hotPosts", postService.getHotPosts());
		model.addAttribute("posts", postService.findNewest(6, 0));
		
		return "post/category";
	}
	
	@Autowired
	private BannerService bannerService;
	
	@GetMapping("/{postSlug}.{postId}.html")
	public String showPost(@PathVariable("postSlug") String postSlug, @PathVariable("postId") int postId
			, Model model) {
		Post post = postService.findById(postId);
		if (post == null)
			return "";
		String rootPostSlug = post.getSlug();
		if (rootPostSlug.equals(postSlug)) {
			postService.increaseViewCount(post.getId(), post.getViewCount()+1);
			
			model.addAttribute("post", post);
			model.addAttribute("newestPosts", postService.findNewest(4));
			model.addAttribute("randomPosts", postService.getRandomPosts());
			
			model.addAttribute("sideBanners", bannerService.getPostSideBanner());
			model.addAttribute("bottomBanners", bannerService.getPostBottomBanner());
			
			return "post/post";	
		} else return "redirect:/bai-viet/"+rootPostSlug+"."+postId + ".html";
		
	}
}
