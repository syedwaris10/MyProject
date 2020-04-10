package com.incture.employee.dao;

import java.util.List;

import com.incture.employee.dto.LookUpDto;

public interface LookUpDaoLocal {

	public List<LookUpDto> getEmployeeLookUp();

	public void createNewLookUp(LookUpDto lookUpDto);
}
