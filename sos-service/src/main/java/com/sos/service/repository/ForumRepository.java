package com.sos.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.entities.Forum;

public interface ForumRepository extends JpaRepository<Forum, Long>{
	
}