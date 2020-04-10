package com.incture.employee.service;

import java.util.List;

import com.incture.employee.dto.EmployeeStagingDto;
import com.incture.employee.dto.MessageDto;

public interface EmployeeStagingLocal {

	public MessageDto saveEmployeeReecords(EmployeeStagingDto employeedto);

	public List<EmployeeStagingDto> getEmployeeData(int reqId);

	public MessageDto deleteStagingData(int reqId);

	public MessageDto updateEmployeeData(List<EmployeeStagingDto> employeedtolist);

	public void putApprovedDataToMasterTable(List<EmployeeStagingDto> employeedtolist);

}
