package com.incture.employee.dao;

import com.incture.employee.dto.BankDetailsDto;
import com.incture.employee.entity.BankDetailsDo;
import com.incture.employee.util.CommonUtils;

public class BankDetailsDao extends BaseDao<BankDetailsDo, BankDetailsDto> implements BankDetailsDaoLocal {

	@Override
	public void addBankDetails(BankDetailsDto bankDetailsDto) {
		getSession().persist(importDto(bankDetailsDto));
	}

	@Override
	public void updateBankDetails(BankDetailsDto dto) {
		BankDetailsDo entity = (BankDetailsDo) getSession().get(BankDetailsDo.class, dto.getAccountNumber());
		getSession().merge(getUpdatedEntity(entity, dto));
	}

	private BankDetailsDo getUpdatedEntity(BankDetailsDo entity, BankDetailsDto dto) {
		if (CommonUtils.checkForDataToUpdate(dto.getAccountNumber())) {
			entity.setAccountNumber(dto.getAccountNumber());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getAccountType())) {
			entity.setAccountType(dto.getAccountType());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getBankName())) {
			entity.setBankName(dto.getBankName());
		}
		return entity;
	}

	@Override
	public BankDetailsDo importDto(BankDetailsDto dto) {
		BankDetailsDo entity = new BankDetailsDo();
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setAccountType(dto.getAccountType());
		entity.setBankName(dto.getBankName());
		entity.setEmployeeCode(dto.getEmployeeCode());
		return entity;
	}

	@Override
	public BankDetailsDto exportDto(BankDetailsDo entity) {
		BankDetailsDto dto = new BankDetailsDto();
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setAccountType(entity.getAccountType());
		dto.setBankName(entity.getBankName());
		dto.setEmployeeCode(entity.getEmployeeCode());
		return dto;
	}

}
