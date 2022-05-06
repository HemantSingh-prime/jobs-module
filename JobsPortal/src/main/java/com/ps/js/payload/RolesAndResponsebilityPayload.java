package com.ps.js.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RolesAndResponsebilityPayload {

	private int id;
	@NotNull
	@NotBlank
	private String rolesResponsebility;
}
