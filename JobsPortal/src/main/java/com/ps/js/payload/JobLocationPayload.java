package com.ps.js.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



import lombok.Data;

@Data
public class JobLocationPayload {


	private int locationId;
	private String locationName;
}
