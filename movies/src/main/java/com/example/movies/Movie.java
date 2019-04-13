package com.example.movies;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Movie {

	private @Id @GeneratedValue Long id;
	private String name;
	private String imdbLink;
	private String description;

	private Movie() {}

	public Movie(String name, String imdbLink, String description) {
		this.name = name;
		this.imdbLink = imdbLink;
		this.description = description;
	}
}