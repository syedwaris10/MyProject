/**
 * 
 */
package com.incture.employee.service;

import java.util.List;

import com.incture.employee.dto.EmployeeStagingDto;

public interface EmployeeDataLocal {
	public void saveEmployeePersonalData(List<EmployeeStagingDto> employeedtolist);
}
