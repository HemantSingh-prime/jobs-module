package com.ps.js.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



import lombok.Data;

@Data
public class JobLocationPayload {

    
	private int locationId;
	@NotNull(message = "Country should not be null")
	@NotBlank(message = "Country should not be empty")
	private String country;
	@NotNull(message = "Address should not be null")
	@NotBlank(message = "Address should not be empty")
	private  String address;
	@NotNull(message = "Continent should not be null")
	@NotBlank(message = "Continent should not be empty")
	private String continent;
	@NotNull(message = "region should not be null")
	@NotBlank(message = "region should not be empty")
	private String region;
}
