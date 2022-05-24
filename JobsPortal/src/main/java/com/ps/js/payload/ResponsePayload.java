package com.ps.js.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ResponsePayload {

	@Min(message = "Id should be greater then 0",value = 1)
	private int id;
	@NotNull(message = "url should not null")
	@NotEmpty(message = "url should not be empty")
	private String url;
}
