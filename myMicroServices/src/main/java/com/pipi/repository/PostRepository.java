package com.pipi.repository;

import com.pipi.bean.PostJPA;
import com.pipi.bean.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostJPA, Integer> {

}
