package com.example.urlshortener.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.entity.urlLoader;
import java.util.List;

public interface UrlRepository extends JpaRepository<urlLoader,Long>{

	boolean existsByAlias(String alias);

	

	Optional<urlLoader> findByAlias(String alias);






	List<urlLoader> findAllByUsersId(Long userid);

	

}
