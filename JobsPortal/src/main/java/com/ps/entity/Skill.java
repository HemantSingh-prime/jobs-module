package com.ps.entity;


import javax.persistence.Entity;

import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Skill {

	@Id
	private int skillId;
	private String skillType;
	private String skillName;
	
}
