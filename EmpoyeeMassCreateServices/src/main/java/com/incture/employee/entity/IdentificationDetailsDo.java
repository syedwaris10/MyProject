package com.incture.employee.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class IdentificationDetailsDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDENTIFICATION_NUMBER", length = 15, nullable = false)
	private String identificationNumber;

	@Column(name = "IDENTIFACTION_TYPE", length = 10, nullable = false)
	private String identificationType;

	@Column(name = "EMPLOYEE_CODE", length = 10, nullable = false)
	private String employeeCode;

	/**
	 * @return the identificationNumber
	 */
	public String getIdentificationNumber() {
		return identificationNumber;
	}

	/**
	 * @param identificationNumber
	 *            the identificationNumber to set
	 */
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	/**
	 * @return the identificationType
	 */
	public String getIdentificationType() {
		return identificationType;
	}

	/**
	 * @param identificationType
	 *            the identificationType to set
	 */
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

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

	@Override
	public Object getPrimaryKey() {
		return null;
	}

}
