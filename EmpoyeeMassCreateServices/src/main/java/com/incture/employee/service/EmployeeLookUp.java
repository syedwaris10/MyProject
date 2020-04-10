package com.incture.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.employee.dao.LookUpDaoLocal;
import com.incture.employee.dto.LookUpDto;
import com.incture.employee.dto.MessageDto;
import com.incture.employee.util.CommonUtils;
import com.incture.employee.util.ServiceUtil;

@Service
public class EmployeeLookUp implements EmployeeLookUpLocal {

	@Autowired
	private LookUpDaoLocal lookUpDaoLocal;

	@Override
	public MessageDto createNewLookUp(LookUpDto lookUpDto) {
		if (!ServiceUtil.isEmpty(lookUpDto)) {
			try {
				lookUpDto.setLookUpId(CommonUtils.getRandomUUIDKey());
				lookUpDaoLocal.createNewLookUp(lookUpDto);
			} catch (Exception e) {
				return CommonUtils.getFailedMessage(lookUpDto, e.getMessage());
			}
		}
		return CommonUtils.getSuccessMessage(lookUpDto);
	}

	@Override
	public List<LookUpDto> getAllEmployeeLookUp() {
		List<LookUpDto> list = lookUpDaoLocal.getEmployeeLookUp();
		return list;
	}

}
