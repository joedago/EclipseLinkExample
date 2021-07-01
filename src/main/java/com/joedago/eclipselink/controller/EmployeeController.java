package com.joedago.eclipselink.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joedago.eclipselink.persistence.entity.Employee;
import com.joedago.eclipselink.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	@RequestMapping(method= RequestMethod.GET, path = "")
	public ResponseEntity<Page<Employee>> findAll(
			@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "20") Integer size) {
		Page<Employee> result = employeeService.findAllEmployees(page,size);
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{empNo}")
	public ResponseEntity<Employee> findOne(@PathVariable("empNo") Integer empNo) {
		return ResponseEntity.ok(employeeService.findOne(empNo));
	}
	
	@RequestMapping(method = RequestMethod.POST, path="") 
	public ResponseEntity<Employee> save(@RequestBody @Valid Employee employee) {
		return ResponseEntity.ok(employeeService.saveOne(employee));
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path="/empNo")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("empNo") Integer empNo) {
		employeeService.deleteOne(empNo);
	}
}
