package com.pipi.repository;

import com.pipi.bean.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;

//  [this is an interface]             [user repo]  [bean class, primary key]
public interface UserRepository extends JpaRepository<UserJPA, Integer> {

}
