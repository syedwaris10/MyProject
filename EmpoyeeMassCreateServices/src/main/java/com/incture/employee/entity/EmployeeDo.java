/**
 * 
 */
package com.incture.employee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.incture.employee.pkEntity.EmployeeDoPk;

/**
 * @author Syed.Waris
 *
 */
@Entity
@Table(name = "EmployeeDetails")
public class EmployeeDo implements BaseDo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmployeeDoPk employeeDoPk;
	
	@Column(name = "EMPLOYEE_NAME", length = 30, nullable = false)
	private String name;
	
	@Column(name = "STATUS", length = 8, nullable = false)
	private String status;

	@Column(name = "HIRE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hireDate;

	@Column(name = "DOB", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name = "GENDER", length = 1, nullable = false)
	private String gender;

	@Column(name = "MARITAL_STATUS", length = 1, nullable = false)
	private String maritalStatus;

	@Column(name = "AGE", length = 2, nullable = false)
	private int age;

	/**
	 * @return the employeeDoPk
	 */
	public EmployeeDoPk getEmployeeDoPk() {
		return employeeDoPk;
	}

	/**
	 * @param employeeDoPk the employeeDoPk to set
	 */
	public void setEmployeeDoPk(EmployeeDoPk employeeDoPk) {
		this.employeeDoPk = employeeDoPk;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the hireDate
	 */
	public Date getHireDate() {
		return hireDate;
	}

	/**
	 * @param hireDate the hireDate to set
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
