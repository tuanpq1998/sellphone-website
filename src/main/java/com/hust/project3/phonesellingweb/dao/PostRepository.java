package com.hust.project3.phonesellingweb.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	public Optional<Post> findByIdAndDraftIsFalse(int postId);

	@Transactional
	@Modifying
	@Query("UPDATE Post p SET p.viewCount=:viewCount WHERE p.id=:id")
	public int increaseViewCount(int id, int viewCount);

	public long count();
	
	public List<Post> findTop5ByOrderByViewCountDesc();

	public Slice<Post> findByOrderByCreateAtDesc(Pageable pageable);

	public Page<Post> findByOrderByUpdateAtDescCreateAtDesc(Pageable pageable);

	public Page<Post> findByTitleContainingOrderByCreateAtDescUpdateAtDesc(String searchKey, Pageable pageable);
}
