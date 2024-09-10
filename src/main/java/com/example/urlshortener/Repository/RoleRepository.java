package com.example.urlshortener.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urlshortener.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	Roles findByName(String name);

	
}
