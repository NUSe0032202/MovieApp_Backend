package com.ben.backend.movieapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben.backend.movieapp.model.Entry;
import com.ben.backend.movieapp.service.DataService;

@CrossOrigin(origins = { "*" }, allowedHeaders = "*")
//@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders="*")
@RestController
public class Controller {

	@Autowired
	DataService service;

	@GetMapping("/movie/entries")
	public ResponseEntity<List<Entry>> getEntries() {
		List<Entry> data = service.getEntries();
		return ResponseEntity.ok(data);
	}

	@RequestMapping("/movie/dashboard")
	public String firstPage() {
		return "success";
	}

	@PostMapping("/movie/edit")
	public ResponseEntity<Object> editEntry(@RequestBody Entry data) {
		System.out.println("Edit");
		// service.editEntry(data);
		Validator valid = Validation.buildDefaultValidatorFactory().getValidator();
		List<String> errorMsgs = new ArrayList<String>();
		valid.validate(data).stream().forEach(violation -> errorMsgs.add(violation.getMessage()));
		if (errorMsgs.size() > 0) {
			return new ResponseEntity<Object>(errorMsgs, HttpStatus.BAD_REQUEST);
		} else {
			service.editEntry(data);
			errorMsgs.add("Your changes have been saved");
			return ResponseEntity.ok(errorMsgs);
		}
	}

	@PostMapping("/movie/delete")
	public ResponseEntity<String> deleteEntry(@RequestBody String title) {
		System.out.println("Movie to delete: " + title);
		service.deleteEntry(title);
		// To return response back to front-end if the delete is successful
		// return ResponseEntity.noContent().build();
		return ResponseEntity.ok(null);
	}

	@PostMapping("/movie/create")
	public ResponseEntity<Object> createEntry(@RequestBody Entry data) {
		System.out.println("Update");
		Validator valid = Validation.buildDefaultValidatorFactory().getValidator();
		List<String> errorMsgs = new ArrayList<String>();
		valid.validate(data).stream().forEach(violation -> errorMsgs.add(violation.getMessage()));
		if (errorMsgs.size() > 0) {
			return new ResponseEntity<Object>(errorMsgs, HttpStatus.BAD_REQUEST);
		} else {
			service.insertEntry(data);
			errorMsgs.add("Entry has been added");
			return ResponseEntity.ok(errorMsgs);
		}
	}
}
