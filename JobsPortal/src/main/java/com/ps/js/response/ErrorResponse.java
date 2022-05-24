package com.ps.js.response;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {

	private String message;
	private List<String> details;
}
