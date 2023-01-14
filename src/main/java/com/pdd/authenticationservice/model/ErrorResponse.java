package com.pdd.authenticationservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pdd.authenticationservice.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ErrorResponse {

	private final int httpCode;
	private final String errorType;
	private List<String> errorMessages;
	
	public String errorMessagesAsList() {
		return (errorMessages==null)?Constants.EMPTY_STRING:errorMessages.stream().collect(Collectors.joining(","));
	}
	
	public void addErrorMessage(String errorMessage) {
		if(errorMessages==null) {
			errorMessages = new ArrayList<>();
		}
		errorMessages.add(errorMessage);
	}
}
