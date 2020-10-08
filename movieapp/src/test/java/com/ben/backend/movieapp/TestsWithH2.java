package com.ben.backend.movieapp;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;

import com.ben.backend.movieapp.model.Entry;
import com.ben.backend.movieapp.service.DataService;


public class TestsWithH2 {
	
	DataSource dataSource;
	
	DataService testService;
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@BeforeEach
	public void setup() {
		dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
		          .generateUniqueName(true)
		          .addScript("classpath:jdbc/schema.sql")
		          .addScript("classpath:jdbc/test-data.sql")
		          .build();
		testService = new DataService();
		jdbcTemplate = new JdbcTemplate(dataSource);
		ReflectionTestUtils.setField(testService, "template", jdbcTemplate);
	}
	
	@Test
	public void whenUsingH2_ReturnCorrectEntries() {
		List<Entry> retrievedData = testService.getEntries();
		assertThat(retrievedData.get(0)).extracting(Entry::getMovieTitle).containsExactly("Frozen");
	}
	
	@Test
	public void whenUsingH2_CorrectEntryDeleted() {
		testService.deleteEntry("Frozen");
		List<Entry> retrievedData = testService.getEntries();
		assertThat(retrievedData).filteredOn(entry->"Frozen".equals(entry.getMovieTitle())).isEmpty();
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenUsingH2_SuccessfulEdit() {
		Entry edit = new Entry();
		edit.setMovieTitle("Frozen");
		edit.setMovieGenre("Romance");
		edit.setMovieLanguage("English");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 26);
		Date testDate = cal.getTime();
	    edit.setMovieYear(testDate);
	    edit.setMovieRating(2);
	    List<String> list = Arrays.asList("Peter","Betty Khoo");
	    //edit.setMovieActor(new String[]{"Peter","Betty Khoo"});
	    edit.setMovieActor(list);
	    
	    testService.editEntry(edit);
	    List<Entry> retrievedData = testService.getEntries();
	    //String[] newActors = new String[]{"Peter","Betty Khoo"};
	    List<String> newActors = Arrays.asList("Peter","Betty Khoo");
	    assertThat(retrievedData).filteredOn(entry->"Frozen".equals(entry.getMovieTitle()))
	    .extracting(Entry::getMovieActor).containsExactlyInAnyOrder(newActors);	
	}
	
	@Test
	public void whenUsingH2_SuccessfulInsert() {
		Entry edit = new Entry();
		edit.setMovieTitle("Mission Impossible");
		edit.setMovieGenre("Action");
		edit.setMovieLanguage("English");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 26);
		Date testDate = cal.getTime();
	    edit.setMovieYear(testDate);
	    edit.setMovieRating(3);
	    List<String> list = Arrays.asList("Tom Cruiuse","Simon Pegg","Henry Cavill");
	    //edit.setMovieActor(new String[]{"Tom Cruiuse","Simon Pegg","Henry Cavill"});
	    edit.setMovieActor(list);
	    
	    testService.insertEntry(edit);
	    List<Entry> retrievedData = testService.getEntries();
	    assertThat(retrievedData).filteredOn(entry->"Mission Impossible".equals(entry.getMovieTitle())).isNotEmpty();
	}
	
}
