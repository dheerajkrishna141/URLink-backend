package com.example.urlshortener.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.entity.User;



public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String userName);

}
