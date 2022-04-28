package com.ps.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;



import lombok.Data;

@Data
@Entity
public class JobLocation {

	@Id
	@Column(name="location_id")
	private int locationId;
	private String locationName;
	
}
