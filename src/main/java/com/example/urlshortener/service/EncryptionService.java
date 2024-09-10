package com.example.urlshortener.service;

public interface EncryptionService {
	
	public String EncryptPassword(String password);
	
	public boolean VerifyPassword(String password, String hash);

}
