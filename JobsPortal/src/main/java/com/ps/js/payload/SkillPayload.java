package com.ps.js.payload;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class SkillPayload implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private int skillId;
	@NotNull
	@NotBlank
	private String skillName;
}
