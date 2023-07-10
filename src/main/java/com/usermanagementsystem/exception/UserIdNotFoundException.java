package com.usermanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIdNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserIdNotFoundException(String string) {
		super(string);
	}

}
