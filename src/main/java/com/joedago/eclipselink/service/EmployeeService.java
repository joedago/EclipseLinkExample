package com.joedago.eclipselink.service;

import org.springframework.data.domain.Page;

import com.joedago.eclipselink.persistence.entity.Employee;

public interface EmployeeService {

	Page<Employee> findAllEmployees(Integer page, Integer size);
	Employee findOne(Integer empNo);
	void deleteOne(Integer empNo);
	Employee saveOne(Employee employee);
}
