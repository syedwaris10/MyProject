package com.incture.employee.dao;

import org.springframework.stereotype.Repository;

import com.incture.employee.dto.IdentificationDetailsDto;
import com.incture.employee.entity.IdentificationDetailsDo;

@Repository("IdentificationDetailsDao")
public class IdentificationDetailsDao extends BaseDao<IdentificationDetailsDo, IdentificationDetailsDto>
		implements IdentificationDetailsDaoLocal {

	@Override
	public void saveEmployeeIdentifactionDetails(IdentificationDetailsDto dto) {
		getSession().persist(importDto(dto));
	}

	@Override
	public void updateIdentificationDetails(IdentificationDetailsDto dto) {
		/*IdentificationDetailsDo entity = (IdentificationDetailsDo) getSession().get(IdentificationDetailsDo.class,
				dto.getIdentificationNumber());
		 getSession().merge(getUpdatedEntity(entity, dto));*/
	}

	@Override
	public IdentificationDetailsDo importDto(IdentificationDetailsDto dto) {
		IdentificationDetailsDo entity = new IdentificationDetailsDo();
		entity.setEmployeeCode(dto.getEmployeeCode());
		entity.setIdentificationNumber(dto.getIdentificationNumber());
		entity.setIdentificationType(dto.getIdentificationType());
		return entity;
	}

	@Override
	public IdentificationDetailsDto exportDto(IdentificationDetailsDo entity) {
		IdentificationDetailsDto dto = new IdentificationDetailsDto();
		dto.setEmployeeCode(entity.getEmployeeCode());
		dto.setIdentificationNumber(entity.getIdentificationNumber());
		dto.setIdentificationType(entity.getIdentificationType());
		return dto;
	}
}
