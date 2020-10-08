package com.ben.backend.movieapp.model;

import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;

public class Entry {
	
	@NotBlank(message="Movie Titile cannot be blank")
	private String movieTitle;
	
	private String movieLanguage;
	
	private String movieGenre;
	
	private Date movieYear;
	
	private int movieRating;
	
	private List<@NotBlank(message="Actor cannot be blank") String> movieActor;
	
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public String getMovieLanguage() {
		return movieLanguage;
	}
	public void setMovieLanguage(String movieLanguage) {
		this.movieLanguage = movieLanguage;
	}
	
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	
	public Date getMovieYear() {
		return movieYear;
	}
	public void setMovieYear(Date movieYear) {
		this.movieYear = movieYear;
	}
	
	public int getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(int movieRating) {
		this.movieRating = movieRating;
	}
	
	public List<String> getMovieActor() {
		return movieActor;
	}
	public void setMovieActor(List<String> movieActor) {
		this.movieActor = movieActor;
	}
	
	

}
