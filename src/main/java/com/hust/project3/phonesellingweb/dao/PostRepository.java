package com.hust.project3.phonesellingweb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.project3.phonesellingweb.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	public Optional<Post> findByIdAndDraftIsFalse(int postId);

}
