package com.incture.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employement_Details")
public class EmploymentDetailsDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMPLOYEE_CODE", length = 10, nullable = false)
	private String employeeCode;

	@Column(name = "COMPANY", length = 50)
	private String company;

	@Column(name = "LOCATION", length = 20)
	private String location;

	@Column(name = "DEPARTMENT", length = 20)
	private String department;

	@Column(name = "EMPLOYMENT_TYPE", length = 10, nullable = false)
	private String employmentType;

	@Column(name = "DESIGNATION", length = 20, nullable = false)
	private String designation;

	@Column(name = "PF_CODE", length = 15)
	private String pfCode;

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
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType
	 *            the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation
	 *            the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the pfCode
	 */
	public String getPfCode() {
		return pfCode;
	}

	/**
	 * @param pfCode
	 *            the pfCode to set
	 */
	public void setPfCode(String pfCode) {
		this.pfCode = pfCode;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
