package com.csvdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csvdb.entity.Employee;
import com.csvdb.exception.Counter;
import com.csvdb.exception.Response;
import com.csvdb.serviceimpl.EmployeeServiceImpl;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeServiceiImpl;

	@PostMapping("/addemp")
	public ResponseEntity<Response> addEmployee(@ModelAttribute Employee employee) {
		if (this.employeeServiceiImpl.addEmployee(employee))
			return Response.generateResponse("CREATED", HttpStatus.CREATED, employee);
		else
			return Response.generateResponse("BadRequest", HttpStatus.INTERNAL_SERVER_ERROR, null);

	}

	@PostMapping("/upload")
	 public ResponseEntity<Response> saveData(@RequestParam("file") MultipartFile file) throws Exception{
		Counter counter=this.employeeServiceiImpl.saveCSVData(file);
		if (counter.getProcessedLines()>0 || counter.getSkippedLines()>0) {
			return Response.generateResponse("DATA SAVED SUCCESSFULLY", HttpStatus.CREATED, counter);
		}
		else
			return Response.generateResponse("BadRequest", HttpStatus.INTERNAL_SERVER_ERROR, null);

	}
	}
