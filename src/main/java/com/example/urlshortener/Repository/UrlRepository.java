package com.example.urlshortener.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.entity.Url;
import com.example.urlshortener.entity.User;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {

	boolean existsByAlias(String alias);

	Optional<Url> findByAlias(String alias);

	Page<Url> findAllByUsers(Pageable pg, User user);

	List<Url> findAllByUsersId(Long id);

}
