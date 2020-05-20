package com.dbs.assignment;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.assignment.service.InfoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/info")
public class AssignmentController {

	@Autowired
	InfoService infoService;
	
	/**
	 * Fetching the list of both files and directories of the given path
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping(path = "/directory", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDirectoryInfo(@RequestParam(value = "path") String path) {
		try {
			return infoService.filesAndDirectories(path);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Method to get the file description
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getFileInfo(@RequestParam(value = "path") String path) {
		try {
			return infoService.getFileDescription(path);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}