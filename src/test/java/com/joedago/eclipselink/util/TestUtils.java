package com.joedago.eclipselink.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joedago.eclipselink.persistence.entity.Employee;
import com.joedago.eclipselink.persistence.entity.Gender;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestUtils {
	
	private TestUtils() {
		
	}
		
	private static ObjectMapper mapper;
	
	static{
		mapper = new ObjectMapper();
	}
	
	public static Employee getExistentEmployee() {
		
		return getExistentEmployee(1);
	}
	
	public static Employee getExistentEmployee(Integer empNo) {
		Employee employee = getNewEmployee();
		employee.setEmpNo(empNo);
		return employee;
	}
	
	public static Employee getNewEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setGender(Gender.M);
		employee.setBirthDate(new Date(Calendar.getInstance().getTimeInMillis()));
		employee.setHireDate(new Date(Calendar.getInstance().getTimeInMillis()));
		return employee;
	}
	
	public static Employee getEmployeeFromJsonString(String json) {
		try {
			return mapper.readValue(json, Employee.class);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static List<Employee> getEmployeeListFromJsonString(String json) {
		try {
			return mapper.readValue(json, 
					mapper.getTypeFactory()
					.constructCollectionType(List.class,Employee.class));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static List<Employee> getEmployeePageFromJsonString(String json) {
		try {
			JsonNode node = mapper.readTree(json);
			return getEmployeeListFromJsonString(node.get("content").toString());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static List<Employee> getExistentEmployeeList() {
		List<Employee> employees = new ArrayList<>();
		for(int i = 1; i <= 3; i++) {
			employees.add(getExistentEmployee(i));
		}
		return employees;
	}
	
	public static String getJsonFromEmployee(Employee employee) {
		try {
			return mapper.writeValueAsString(employee);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static String getJsonFromNewEmployee() {
		return getJsonFromEmployee(getNewEmployee());
	}

}
