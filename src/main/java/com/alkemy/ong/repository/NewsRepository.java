package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, String>{

}
