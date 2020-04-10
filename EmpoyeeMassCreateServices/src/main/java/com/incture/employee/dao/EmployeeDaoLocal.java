package com.incture.employee.dao;

import java.text.ParseException;
import java.util.List;

import com.incture.employee.dto.EmployeeDto;

public interface EmployeeDaoLocal {

	public void saveEmployeePersonalData(List<EmployeeDto> emploeeList);

	public void updateEmployeePersonalDetails(EmployeeDto emploeeDto) throws ParseException;

}
