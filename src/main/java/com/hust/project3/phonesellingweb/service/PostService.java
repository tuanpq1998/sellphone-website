package com.hust.project3.phonesellingweb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.hust.project3.phonesellingweb.dao.PostRepository;
import com.hust.project3.phonesellingweb.entity.Post;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	private int numRandomPost = 4;

	public Post findById(int postId) {
		Optional<Post> result = postRepository.findByIdAndDraftIsFalse(postId);
		Post post = null;
		if (result.isPresent())
			post = result.get();
		return post;
	}

	public List<Post> findNewest(int numpost) {
		Slice<Post> slice = findNewest(numpost, 0);
		return slice.getContent();
	}
	
	public Slice<Post> findNewest(int numpost, int page) {
		return postRepository.findByOrderByCreateAtDesc(PageRequest.of(page, numpost));
	}

	public void increaseViewCount(int id, int viewCount) {
		postRepository.increaseViewCount(id, viewCount);
	}
	
	public List<Post> getRandomPosts() {
		int total = countAll();
		int totalPages = (total % numRandomPost == 0) ? (total / numRandomPost) : (total / numRandomPost)+1;
		int pageIndex = (int) (Math.random()*totalPages);
		PageRequest request = PageRequest.of(pageIndex, numRandomPost);
		Page<Post> page = postRepository.findAll(request);
		List<Post> result = new ArrayList<Post>(page.getContent());
		Collections.shuffle(result);
		return result;
	}

	public int countAll() {
		return (int) postRepository.count();
	}
	
	public List<Post> getHotPosts() {
		return postRepository.findTop5ByOrderByViewCountDesc();
	}

	public Page<Post> findAll(int i, Integer page) {
		Pageable pageable = PageRequest.of(page - 1, i);
		return postRepository.findByOrderByUpdateAtDescCreateAtDesc(pageable);
	}

	public Page<Post> findAllLike(String searchKey, int i, Integer page) {
		Pageable pageable = PageRequest.of(page - 1, i);
		return postRepository.findByTitleContainingOrderByUpdateAtDescCreateAtDesc(searchKey, pageable);
	}

	public void save(Post post) {
		postRepository.save(post);
	}
	
	
}
