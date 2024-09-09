package com.example.urlshortener.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class AliasNotFoundException extends RuntimeException{
	private String mes;

	public AliasNotFoundException(String mes) {
		super(mes);
		this.mes = mes;
	}
	

}
