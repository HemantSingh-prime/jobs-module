package com.ps.js.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;

@Data
@Entity
public class JobLocation {

	@Id
	@Column(name="location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int locationId;
	@Column(name="location_name")
	private String locationName;
	
}
