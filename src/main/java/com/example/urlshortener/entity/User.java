package com.example.urlshortener.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userName", nullable = false)
	private String username;
	
	@Column(name = "firstName")
	private String firstname;
	
	@Column(name = "lastName")
	private String lastname;

	@Column(name = "password", nullable = false)
	private String password;
}