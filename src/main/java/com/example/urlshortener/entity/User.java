package com.example.urlshortener.entity;

import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(nullable = false)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Roles> roles = new HashSet<Roles>();
	
	@OneToMany(orphanRemoval = true, mappedBy = "users", cascade = CascadeType.ALL)
	private Set<Url> urls = new HashSet<Url>();
}