package com.example.urlshortener.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class urlUpdateDTO {

	String alias;
	String newUrl;
}
