package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Botanic {
	
	private Integer id;
	private Location location;
	
	@NotBlank
	private String botanicCpl;
	
	@NotBlank
	@Size(max = 50)
	private String botanicName;
	private String japaneseName;
	private String scientificName;
	private String genusName;
	private String description;
	private String imagePath; 
	
}
