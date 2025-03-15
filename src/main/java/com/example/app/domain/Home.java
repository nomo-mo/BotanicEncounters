package com.example.app.domain;

import lombok.Data;

@Data
public class Home {
	
	private Integer id;
	private Location location;
	private String plantCpl;
	private String plantName;
	private String japaneseName;
	private String scientificName;
	private String genusName;
	private String description;
	private String imagePath; 
	

}
