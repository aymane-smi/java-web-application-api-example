package com.example.authsystemspring.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class userException extends RuntimeException{

	public userException(String message) {
		super(message);
	}

}
