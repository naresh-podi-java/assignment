package com.dbs.assignment.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dbs.assignment.beans.FileInfo;
import com.dbs.assignment.beans.FilesAndDirectories;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class InfoService {

	/**
	 * Fetching the list of both files and directories of the given path
	 * 
	 * @param path
	 * @return
	 * @throws JsonProcessingException
	 */
	public String filesAndDirectories(String path) throws JsonProcessingException {
		File rootPath = new File(path);
		Collection<File> fileList = getFilesAndDirectories(rootPath);

		List<String> files = new ArrayList<>();
		List<String> dirs = new ArrayList<>();

		for (File file : fileList) {
			if (file.isDirectory()) {
				dirs.add(file.getAbsolutePath());
			} else {
				files.add(file.getAbsolutePath());
			}
		}

		FilesAndDirectories filesAndDirectories = new FilesAndDirectories();
		filesAndDirectories.setDirectories(dirs);
		filesAndDirectories.setFiles(files);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper.writeValueAsString(filesAndDirectories);
	}
	
	/**
	 * Method to get the file description
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String getFileDescription(String path) throws IOException {
		File file = new File(path);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setName(file.getName());
		fileInfo.setParent(file.getParent());
		fileInfo.setCanWrite(file.canWrite());
		fileInfo.setCanRead(file.canRead());
		fileInfo.setCanExecute(file.canExecute());
		fileInfo.setIsAbsolute(file.isAbsolute());
		fileInfo.setIsDirectory(file.isDirectory());
		fileInfo.setIsFile(file.isFile());
		fileInfo.setIsHidden(file.isHidden());
		fileInfo.setPath(file.getPath());
		fileInfo.setCanonicalPath(file.getCanonicalPath());
		fileInfo.setAbsolutePath(file.getAbsolutePath());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		return objectMapper.writeValueAsString(fileInfo);
	}
	
	private static Collection<File> getFilesAndDirectories(File dir) {
		Set<File> fileTree = new HashSet<File>();
		if(dir == null || dir.listFiles() == null) {
			return fileTree;
		}

		for (File entry : dir.listFiles()) {
			if (entry.isFile()) {
				fileTree.add(entry);
			} else {
				fileTree.add(entry);
				fileTree.addAll(getFilesAndDirectories(entry));
			}
		}
		return fileTree;
	}

}
