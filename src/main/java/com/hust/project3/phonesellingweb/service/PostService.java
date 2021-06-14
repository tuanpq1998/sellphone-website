package com.hust.project3.phonesellingweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.PostRepository;
import com.hust.project3.phonesellingweb.entity.Post;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post findById(int postId) {
		Optional<Post> result = postRepository.findByIdAndDraftIsFalse(postId);
		Post post = null;
		if (result.isPresent())
			post = result.get();
		return post;
	}

	public List<Post> findNewest() {
		return postRepository.findTop4ByOrderByCreateAtAsc();
	}
	
	
}
