package com.incture.employee.dto;

public class LookUpDto extends BaseDto {

	private String lookUpId;
	private String lookUp;
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
