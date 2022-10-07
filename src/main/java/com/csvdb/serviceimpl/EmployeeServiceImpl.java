package com.csvdb.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
		if(this.employeeRepository.save(employee)!=null) {
		return true;
		}
		return false;
	}
	
	public Counter saveCSVData(MultipartFile file) throws IOException 
	{
			int processedLine =0;
			int skippedLine = 0;
			
			List<Employee> employeeData = new ArrayList<>();
			InputStream inputStream =  file.getInputStream();
			CsvParserSettings setting = new CsvParserSettings();
			setting.setHeaderExtractionEnabled(true);
			CsvParser parser = new CsvParser(setting);
			List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
			
			
			for(int i=0;i<parseAllRecords.size();i++) {
				Employee employee = new Employee();
				Record record=parseAllRecords.get(i);
				
				try {	
				System.out.println("Inside");
				if (!record.getString("EmployeeId").equals("") && !record.getString("employee_name").equals("") && !record.getString("age").equals("") && !record.getString("country").equals("")) 
				{	System.out.println("11");
					employee.setId(Integer.parseInt(record.getString("EmployeeId")));
					employee.setName(record.getString("employee_name"));
					employee.setAge(Integer.parseInt(record.getString("age")));
					employee.setCountry(record.getString("country"));
					employeeData.add(employee);
					processedLine++;
				}
				} catch (Exception e) {
					 System.out.println("Outside");
					skippedLine++;
				}
			}
			
			Counter counter=new Counter();
			counter.setProcessedLines(processedLine);
			counter.setSkippedLines(skippedLine);
			
			System.out.println(employeeData);
			System.out.println("Here");
			if(this.employeeRepository.saveAll(employeeData)!=null) {
				return counter;
				}
				return counter;
			}

}
