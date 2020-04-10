package com.incture.employee.dao;

import java.util.List;

import com.incture.employee.dto.EmployeeStagingDto;
import com.incture.employee.exceptions.InvalidInputFault;

public interface EmployeeStagingDaoLocal {

	public void saveEmployee(EmployeeStagingDto dto);

	public List<EmployeeStagingDto> fetchEmployeeStagingData(int reqId);

	public void deleteEmployeeStagingData(int reqId);

	public void updateEmployeeStatus(EmployeeStagingDto dto) throws InvalidInputFault;
}
