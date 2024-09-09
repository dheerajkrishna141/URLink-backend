package com.example.urlshortener.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.entity.Url;
import java.util.List;

public interface UrlRepository extends JpaRepository<Url,Long>{

	boolean existsByAlias(String alias);

	

	Optional<Url> findByAlias(String alias);






	List<Url> findAllByUsersId(Long userid);

	

}
