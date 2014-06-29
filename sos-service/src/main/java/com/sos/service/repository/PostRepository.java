package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
}