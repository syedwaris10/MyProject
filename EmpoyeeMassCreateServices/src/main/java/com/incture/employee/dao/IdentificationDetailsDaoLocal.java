package com.incture.employee.dao;

import com.incture.employee.dto.IdentificationDetailsDto;

public interface IdentificationDetailsDaoLocal {

	public void saveEmployeeIdentifactionDetails(IdentificationDetailsDto dto);

	public void updateIdentificationDetails(IdentificationDetailsDto dto);
}
