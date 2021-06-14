package com.hust.project3.phonesellingweb.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hust.project3.phonesellingweb.entity.Post;
import com.hust.project3.phonesellingweb.service.PostService;

@RestController
public class RestPostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/bai-viet/loadmore")
	public ResponseEntity<?> showMorePost(@RequestParam("page") int page) {
		Slice<Post> slice = postService.findNewest(6, page);
		return new ResponseEntity<Slice<Post>>(slice, HttpStatus.OK);
	}
}
