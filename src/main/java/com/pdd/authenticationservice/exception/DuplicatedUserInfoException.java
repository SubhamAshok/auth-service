package com.pdd.authenticationservice.exception;

public class DuplicatedUserInfoException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedUserInfoException(String message) {
        super(message);
    }
}
