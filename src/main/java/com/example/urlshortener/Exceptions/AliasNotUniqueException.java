package com.example.urlshortener.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class AliasNotUniqueException extends RuntimeException{
	private String mes;
	public AliasNotUniqueException(String mes) {
		super(mes);
		this.mes = mes;
		
	}

}
