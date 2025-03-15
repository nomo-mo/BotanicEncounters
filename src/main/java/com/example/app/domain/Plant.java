package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Plant {
	
	private Integer id;
	private Location location;
	
	@NotBlank
	private String plantCpl;
	
	@NotBlank
	@Size(max = 50)
	private String plantName;
	private String japaneseName;
	private String scientificName;
	private String genusName;
	private String description;
	private String imagePath; 
	
}
