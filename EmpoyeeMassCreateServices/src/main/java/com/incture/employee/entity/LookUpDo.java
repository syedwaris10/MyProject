package com.incture.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee_LookUp")
public class LookUpDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LookUp_Id", length = 36, nullable = false)
	private String lookUpId;

	@Column(name = "LookUp", nullable = false)
	private String lookUp;

	@Column(name = "Value")
	private String lookUpValue;

	/**
	 * @return the lookUp
	 */
	public String getLookUp() {
		return lookUp;
	}

	/**
	 * @param lookUp
	 *            the lookUp to set
	 */
	public void setLookUp(String lookUp) {
		this.lookUp = lookUp;
	}

	/**
	 * @return the lookUpValue
	 */
	public String getLookUpValue() {
		return lookUpValue;
	}

	/**
	 * @param lookUpValue
	 *            the lookUpValue to set
	 */
	public void setLookUpValue(String lookUpValue) {
		this.lookUpValue = lookUpValue;
	}

	@Override
	public Object getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the lookUpId
	 */
	public String getLookUpId() {
		return lookUpId;
	}

	/**
	 * @param lookUpId
	 *            the lookUpId to set
	 */
	public void setLookUpId(String lookUpId) {
		this.lookUpId = lookUpId;
	}

}
