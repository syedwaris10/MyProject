package com.incture.employee.pkEntity;

import java.io.Serializable;

import javax.persistence.Column;

public class EmployeeDoPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "EMPLOYEE_CODE", length = 10, nullable = false)
	private String employeeCode;

	@Column(name = "EMAIL_ID", length = 50, nullable = false)
	private String email;

	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * @param employeeCode
	 *            the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
