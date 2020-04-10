package com.incture.employee.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.incture.employee.dto.EmploymentDetailsDto;
import com.incture.employee.entity.EmploymentDetailsDo;
import com.incture.employee.util.CommonUtils;

@Repository("EmploymentDetailsDao")
public class EmploymentDetailsDao extends BaseDao<EmploymentDetailsDo, EmploymentDetailsDto>
		implements EmploymentDetailsDaoLocal {

	@Override
	public void saveEmploymentDetails(EmploymentDetailsDto employmentDetailsDto) {
		getSession().persist(importDto(employmentDetailsDto));
	}

	@Transactional
	@Override
	public void updateEmploymentDetails(EmploymentDetailsDto employmentDetailsDto) {
		EmploymentDetailsDo entity = (EmploymentDetailsDo) getSession().get(EmploymentDetailsDo.class,
				employmentDetailsDto.getEmployeeCode());
		getSession().merge(getUpdatedEntity(entity, employmentDetailsDto));
	}

	private EmploymentDetailsDo getUpdatedEntity(EmploymentDetailsDo entity, EmploymentDetailsDto dto) {

		if (CommonUtils.checkForDataToUpdate(dto.getCompany())) {
			entity.setCompany(dto.getCompany());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getDepartment())) {
			entity.setDepartment(dto.getDepartment());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getDesignation())) {
			entity.setDesignation(dto.getDesignation());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getEmploymentType())) {
			entity.setEmploymentType(dto.getEmploymentType());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getLocation())) {
			entity.setLocation(dto.getLocation());
		}
		if (CommonUtils.checkForDataToUpdate(dto.getPfCode())) {
			entity.setPfCode(dto.getPfCode());
		}
		return entity;
	}

	@Override
	public EmploymentDetailsDo importDto(EmploymentDetailsDto dto) {
		EmploymentDetailsDo entity = new EmploymentDetailsDo();
		entity.setCompany(dto.getCompany());
		entity.setDepartment(dto.getDepartment());
		entity.setDesignation(dto.getDesignation());
		entity.setEmployeeCode(dto.getEmployeeCode());
		entity.setEmploymentType(dto.getEmploymentType());
		entity.setLocation(dto.getLocation());
		entity.setPfCode(dto.getPfCode());
		return entity;
	}

	@Override
	public EmploymentDetailsDto exportDto(EmploymentDetailsDo entity) {
		EmploymentDetailsDto dto = new EmploymentDetailsDto();
		dto.setCompany(entity.getCompany());
		dto.setDepartment(entity.getDepartment());
		dto.setDesignation(entity.getDesignation());
		dto.setEmployeeCode(entity.getEmployeeCode());
		dto.setEmploymentType(entity.getEmploymentType());
		dto.setLocation(entity.getLocation());
		dto.setPfCode(entity.getPfCode());
		return dto;
	}
}
