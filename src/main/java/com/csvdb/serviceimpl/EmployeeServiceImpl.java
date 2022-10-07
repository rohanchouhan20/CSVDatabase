package com.csvdb.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csvdb.controller.EmployeeController;
import com.csvdb.entity.Employee;
import com.csvdb.exception.Counter;
import com.csvdb.repository.EmployeeRepository;
import com.csvdb.service.EmployeeService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	String line = "";

	@Override
	public boolean addEmployee(Employee employee) {
		if (this.employeeRepository.save(employee) != null) {
			return true;
		}
		return false;
	}

	public Counter saveCSVData(MultipartFile file) throws IOException {
		int processedLine = 0;
		int skippedLine = 0;

		Logger logger = LoggerFactory.getLogger(EmployeeController.class);

		List<Employee> employeeData = new ArrayList<>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseAllRecords = parser.parseAllRecords(inputStream);

		for (int i = 0; i < parseAllRecords.size(); i++) {
			Employee employee = new Employee();
			Record record = parseAllRecords.get(i);

			try {
				int age = Integer.parseInt(record.getString("age"));
				if (record.getString("EmployeeId")!=null && record.getString("employee_name")!=null
						&& record.getString("age")!=null && record.getString("country")!=null) {
					employee.setId(Integer.parseInt(record.getString("EmployeeId")));
					employee.setName(record.getString("employee_name"));
					employee.setAge(record.getString("age"));
					employee.setCountry(record.getString("country"));
					employeeData.add(employee);
					processedLine++;
				} else {
					employee.setId(Integer.parseInt(record.getString("EmployeeId")));
					employee.setName(record.getString("employee_name"));
					employee.setAge(record.getString("age"));
					employee.setCountry(record.getString("country"));
					logger.warn(employee + " ALl VAlues Are Required...");
					skippedLine++;
				}
			} catch (Exception e) {
				employee.setName(record.getString("employee_name"));
				employee.setAge(record.getString("age"));
				employee.setCountry(record.getString("country"));
				logger.warn(employee + " Type Mismatch");
				skippedLine++;
			}
		}

		Counter counter = new Counter();
		counter.setProcessedLines(processedLine);
		counter.setSkippedLines(skippedLine);

		if (this.employeeRepository.saveAll(employeeData) != null) {
			return counter;
		}
		return counter;
	}

}
