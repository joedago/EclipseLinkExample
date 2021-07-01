package com.joedago.eclipselink.service;

import static com.joedago.eclipselink.util.TestUtils.getExistentEmployee;
import static com.joedago.eclipselink.util.TestUtils.getNewEmployee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.eclipselink.persistence.entity.Employee;
import com.joedago.eclipselink.persistence.repository.EmployeeRepository;
import com.joedago.eclipselink.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	EmployeeService employeeService;
	EmployeeRepository employeeRepository;
	Employee employee;
	List<Employee> employees;
	
	@BeforeEach
	public void setup() {
		employeeRepository = Mockito.mock(EmployeeRepository.class);
		employeeService = new EmployeeServiceImpl(employeeRepository);
		employees = TestUtils.getExistentEmployeeList();
		Optional<Employee> optionalEmployee = Optional.of(getExistentEmployee());
		when(employeeRepository.findById(1)).thenReturn(optionalEmployee);
		when(employeeRepository.findById(2)).thenReturn(Optional.empty());
		doNothing().when(employeeRepository)
		.delete(any(Employee.class));
		when(employeeRepository.save(any(Employee.class)))
			.thenReturn(optionalEmployee.get());
		
	}
	
	@Test
	void testFindExistentEmployeeList() {
		when(employeeRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(employees));
		Page<Employee> list = employeeService.findAllEmployees(0,3);
		assertFalse("List should be not empty", list.isEmpty());
	}
	
	@Test
	void testfindUnexistentEmpoyeeList() {
		when(employeeRepository.findAll()).thenReturn(Page.empty());
		assertThrows(ResponseStatusException.class, 
				() -> employeeService.findAllEmployees(0,3));
	}
	
	@Test
	void testFindOneEmpployee() {
		Employee employee = employeeService.findOne(1);
		assertNotNull("Employee should be not null",employee);
	}
	
	@Test
	void testFindOneEmpployeeNotFound() {
		assertThrows(ResponseStatusException.class, 
				() -> employeeService.findOne(2));
	}
	
	@Test
	void testDeleteEmployee() {
		employeeService.deleteOne(1);
	}
	
	@Test
	void testDeleteEmployeeNotFound() {
		assertThrows(ResponseStatusException.class, 
				() -> employeeService.deleteOne(2));
	}
	
	@Test
	void testSaveEmployee() {
		Employee employee = getNewEmployee();
		Employee savedEmployee = employeeService.saveOne(employee);
		assertNotNull("employee should be not null", savedEmployee);
		assertEquals("Employee Id should be equals 1", savedEmployee.getEmpNo(), Integer.valueOf(1));
	}
	
	
	
}
