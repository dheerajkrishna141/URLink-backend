package com.example.urlshortener.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class urlDTO {
	
	private String alias;
	private String url;

}
