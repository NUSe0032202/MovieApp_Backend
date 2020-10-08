package com.ben.backend.movieapp;


import static org.assertj.core.api.Assertions.*;



import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.ben.backend.movieapp.model.Entry;
import com.ben.backend.movieapp.service.DataService;

@ExtendWith(MockitoExtension.class)
class MovieappApplicationTests {

	List<Entry> testList;
		
	DataService testService;
	
	DataSource dataSource;
	
	Entry entry;

	@Mock
	JdbcTemplate jdbcTemplate;
	
	
	@BeforeEach
    public void setup() { 
        testService = new DataService();
		ReflectionTestUtils.setField(testService, "template", jdbcTemplate);

		testList = new ArrayList<Entry>();
		entry = new Entry();
		entry.setMovieTitle("Test Movie");
		entry.setMovieGenre("Action");
		entry.setMovieLanguage("English");
		entry.setMovieRating(2);
		List<String> list = Arrays.asList("test actor","test actor 2");
		entry.setMovieActor(list);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.AUGUST);
		cal.set(Calendar.DAY_OF_MONTH, 26);
		Date testDate = cal.getTime();
		entry.setMovieYear(testDate);
		testList.add(entry);

		Mockito.when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<RowMapper<Entry>>any()))
				.thenReturn(testList);
    }


	@SuppressWarnings("unchecked")
	@Test
	void whenMockJdbcTemplate_ReturnCorrectEntries() {
		List<Entry> entry_test = testService.getEntries();
	    assertThat(entry_test).extracting(Entry::getMovieTitle).containsExactly("Test Movie");
	    assertThat(entry_test).extracting(Entry::getMovieGenre).containsExactly("Action");
	    assertThat(entry_test).extracting(Entry::getMovieLanguage).containsExactly("English");
	    assertThat(entry_test).extracting(Entry::getMovieRating).containsExactly(2);
	    //String[] element = new String[]{"test actor","test actor 2"};
	    List<String> element = Arrays.asList("test actor","test actor 2");
	    assertThat(entry_test).extracting(Entry::getMovieActor).containsExactlyInAnyOrder(element);
	}

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
