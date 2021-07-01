package com.joedago.eclipselink.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="employees", schema="employees")
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="emp_no", unique=true)
	private Integer empNo;
	@Column(name="birth_date", nullable = false)
	@NotNull
	private Date birthDate;
	@Column(name="first_name", nullable = false)
	@NotNull
	private String firstName;
	@Column(name="last_name", nullable = false)
	@NotNull
	private String lastName;
	@Column(name="gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name="hire_date", nullable = false)
	@NotNull
	private Date hireDate;
}
