package com.incture.employee.dao;

import com.incture.employee.dto.EmploymentDetailsDto;

public interface EmploymentDetailsDaoLocal {

	public void saveEmploymentDetails(EmploymentDetailsDto employmentDetailsDto);

	public void updateEmploymentDetails(EmploymentDetailsDto employmentDetailsDto);
}
