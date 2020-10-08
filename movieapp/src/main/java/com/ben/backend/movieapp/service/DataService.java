package com.ben.backend.movieapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ben.backend.movieapp.model.Entry;

@Service
public class DataService {

	@Autowired
	private JdbcTemplate template;

	public List<Entry> getEntries() {
		//Entry temp = new Entry();
		List<Entry> data = template.query(
				"SELECT movie_title, movie_genre, movie_language, movie_date, movie_rating FROM movieDetails",
				new RowMapper<Entry>() {

					@Override
					public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
						Entry temp = new Entry();
						temp.setMovieTitle(rs.getString("movie_title"));
						temp.setMovieGenre(rs.getString("movie_genre"));
						temp.setMovieLanguage(rs.getString("movie_language"));
						temp.setMovieYear(rs.getDate("movie_date"));
						temp.setMovieRating(rs.getInt("movie_rating"));
						//Get the list of actors
						final String actorSelect = "SELECT a.movie_actor FROM movieActors a INNER JOIN"
								+ " movieDetails d ON a.movie_title = d.movie_title WHERE a.movie_title = ? ";
						List<String> actors = template.query(actorSelect,new RowMapper<String>() {

							@Override
							public String mapRow(ResultSet rs, int rowNum) throws SQLException {
								return rs.getString("movie_actor");
							}
							
						}, new Object[] {temp.getMovieTitle()});
						temp.setMovieActor(actors);
						return temp;
					}
				});
		return data;
	}
	
	public void editEntry(Entry entry) {
		//Update the movie details first 
		final String sqlStatement1 = "UPDATE movieDetails SET movie_genre = ? , movie_language = ? , movie_date = ? ,movie_rating = ?"
				+ " WHERE movie_title = ?";
		final String title = entry.getMovieTitle();
		final String genre = entry.getMovieGenre();
		final String lang = entry.getMovieLanguage();
		final int rating = entry.getMovieRating();
		
		Date date1 = entry.getMovieYear();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String formatdate = null;
		try {
			formatdate = format.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		template.update(sqlStatement1,new Object[]{genre,lang,formatdate,rating,title});
		
		//Delete all actors related to the particular movie title 
		final String sqlStatement2 = "DELETE FROM movieActors WHERE movie_title = ?";
		template.update(sqlStatement2,new Object[]{title});
		
		//Add in the revised Actors
		final String sqlStatement3 = "INSERT INTO movieActors (movie_actor,movie_title) VALUES(?,?)";
		for(String name: entry.getMovieActor()) {
			template.update(sqlStatement3, new Object[] {name,title});
		}
	}
	//Delete entry from database
	public void deleteEntry(String title) {
		//movieActors depend on the table movieDetails thus they have to be deleted first
		final String sqlStatement2 = "DELETE FROM movieActors WHERE movie_title = ?";
		template.update(sqlStatement2,new Object[]{title});
		
		final String sqlStatement = "DELETE FROM movieDetails WHERE movie_title = ?";
		template.update(sqlStatement,new Object[]{title});
	}
	
	
	
	public void insertEntry(Entry entry) {
		final String sqlStatement = "INSERT INTO movieDetails (movie_title,movie_genre,movie_language,movie_date,movie_rating"
				+ ") VALUES (?,?,?,?,?)"; 
		
		final String title = entry.getMovieTitle();
		final String genre = entry.getMovieGenre();
		final String lang = entry.getMovieLanguage();
		Date date1 = entry.getMovieYear();
		//System.out.println("Date: "+ date1);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		//Date date = null;
		String formatdate = null;
		try {
			formatdate = format.format(date1);
			//System.out.println("formatted date: "+ formatdate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final int rating = entry.getMovieRating();
		
		template.update(sqlStatement, new Object[] {title,genre,lang,formatdate,rating});
		//Insert actor records
		final String sqlStatement2 = "INSERT INTO movieActors (movie_actor,movie_title) VALUES(?,?)";
		for(String name: entry.getMovieActor()) {
			template.update(sqlStatement2, new Object[] {name,title});
		}
		
		
	}
}
