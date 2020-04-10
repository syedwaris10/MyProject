package com.incture.employee.service;

import java.util.List;

import com.incture.employee.dto.LookUpDto;
import com.incture.employee.dto.MessageDto;

public interface EmployeeLookUpLocal {

	public MessageDto createNewLookUp(LookUpDto lookUpDto);

	public List<LookUpDto> getAllEmployeeLookUp();
}
