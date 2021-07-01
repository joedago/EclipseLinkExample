package com.joedago.eclipselink.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import com.joedago.eclipselink.persistence.entity.Employee;
import com.joedago.eclipselink.service.EmployeeService;
import com.joedago.eclipselink.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;
	
	EmployeeController employeeController;
	
	@MockBean
	EmployeeService employeeService;
	
	@BeforeEach
	public void setup() {
		
		employeeController = new EmployeeController(employeeService);
		when(employeeService.findOne(1)).thenReturn(TestUtils.getExistentEmployee(1));
		when(employeeService.findOne(2)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
		when(employeeService.findAllEmployees(anyInt(), anyInt()))
				.thenReturn(new PageImpl<>(TestUtils.getExistentEmployeeList()));
		when(employeeService.saveOne(any(Employee.class))).thenReturn(TestUtils.getExistentEmployee(1));
	}
	
	@Test
	public void testFindOneEmployee() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/employees/1"))
			.andExpect(status().isOk())
			.andReturn();
		
		Employee returnEmployee = TestUtils.getEmployeeFromJsonString(result.getResponse().getContentAsString());
		
		assertNotNull("Response should be not null", returnEmployee);
	}
	
	@Test
	public void testFindAllEmployees() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/employees"))
			.andExpect(status().isOk())
			.andReturn();
		
		List<Employee> returnEmployees = TestUtils.getEmployeePageFromJsonString(result.getResponse().getContentAsString());
		
		assertNotNull("Response should be not null", returnEmployees);
		assertFalse("Response should be not empty", returnEmployees.isEmpty());
	}
	
	@Test
	public void testSaveEmployee() throws Exception {
		
		String requestBody = TestUtils.getJsonFromNewEmployee();
		MvcResult result = mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isOk())
			.andReturn();
		
		Employee returnEmployee = TestUtils.getEmployeeFromJsonString(result.getResponse().getContentAsString());
		
		assertNotNull("Response should be not null", returnEmployee);
		assertEquals("Response sould have an employee number", returnEmployee.getEmpNo(), Integer.valueOf(1));
	}
	
	@Test
	public void testSaveEmployeeInvalidName() throws Exception {
		
		Employee employee = TestUtils.getNewEmployee();
		employee.setFirstName(null);
		String requestBody = TestUtils.getJsonFromEmployee(employee);
		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest());
		
	}

	
}
;