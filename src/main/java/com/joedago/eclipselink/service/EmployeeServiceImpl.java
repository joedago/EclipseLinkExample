package com.joedago.eclipselink.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.eclipselink.persistence.entity.Employee;
import com.joedago.eclipselink.persistence.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor()
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public Page<Employee> findAllEmployees(final Integer page, final Integer size) {
		
		Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, size));
		if(employees == null || employees.isEmpty()) {
			log.debug("Employees not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return employees;
	}

	@Override
	public Employee findOne(Integer empNo) {
		Optional<Employee> employee = employeeRepository.findById(empNo);
		if(employee.isEmpty()) {
			log.debug("Employee not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return employee.get();
	}

	@Override
	public void deleteOne(Integer empNo) {
		employeeRepository.delete(findOne(empNo));
	}

	@Override
	public Employee saveOne(Employee employee) {
		return employeeRepository.save(employee);
	}

}
