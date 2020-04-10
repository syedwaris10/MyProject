package com.incture.employee.dao;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.incture.employee.dto.EmployeeDto;
import com.incture.employee.entity.EmployeeDo;
import com.incture.employee.pkEntity.EmployeeDoPk;
import com.incture.employee.util.CommonUtils;
import com.incture.employee.util.ServiceUtil;

@Repository("EmployeeDao")
public class EmployeeDao extends BaseDao<EmployeeDo, EmployeeDto> implements EmployeeDaoLocal {

	@Override
	public void saveEmployeePersonalData(List<EmployeeDto> emploeeList) {
		for (EmployeeDto dto : emploeeList) {
			getSession().persist(importDto(dto));
		}
	}

	@Transactional
	@Override
	public void updateEmployeePersonalDetails(EmployeeDto emploeeDto) throws ParseException {
		EmployeeDo entity = (EmployeeDo) getSession().get(EmployeeDo.class, emploeeDto.getEmployeeCode());
		getSession().merge(getUpdatedEntity(entity, emploeeDto));
	}

	private EmployeeDo getUpdatedEntity(EmployeeDo entity, EmployeeDto dto) throws ParseException {
		if (!ServiceUtil.isEmpty(dto.getAge())) {
			entity.setAge(dto.getAge());
		}
		if (!ServiceUtil.isEmpty(dto.getEmail())) {
			entity.getEmployeeDoPk().setEmail(dto.getEmail().trim());
		}
		if (!ServiceUtil.isEmpty(dto.getMaritalStatus())) {
			entity.setMaritalStatus(dto.getMaritalStatus().trim());
		}
		if (!ServiceUtil.isEmpty(dto.getName())) {
			entity.setName(dto.getName().trim());
		}
		if (!ServiceUtil.isEmpty(dto.getStatus())) {
			entity.setStatus(dto.getStatus().trim());
		}
		if (!ServiceUtil.isEmpty(dto.getDob())) {
			if (CommonUtils.dateFormatCheck(dto.getDob())) {
				entity.setDob(dto.getDob());
			}
		}
		if (!ServiceUtil.isEmpty(dto.getHireDate())) {
			if (CommonUtils.dateFormatCheck(dto.getHireDate())) {
				entity.setHireDate(dto.getHireDate());
			}
		}
		return entity;
	}

	@Override
	public EmployeeDo importDto(EmployeeDto dto) {
		EmployeeDo entity = new EmployeeDo();
		EmployeeDoPk pk = new EmployeeDoPk();
		pk.setEmail(dto.getEmail());
		pk.setEmployeeCode(dto.getEmployeeCode());
		entity.setEmployeeDoPk(pk);
		entity.setAge(dto.getAge());
		entity.setDob(dto.getDob());
		entity.setGender(dto.getGender());
		entity.setHireDate(dto.getHireDate());
		entity.setMaritalStatus(dto.getMaritalStatus());
		entity.setName(dto.getName());
		entity.setStatus(dto.getStatus());
		return entity;
	}

	@Override
	public EmployeeDto exportDto(EmployeeDo entity) {
		EmployeeDto dto = new EmployeeDto();
		dto.setAge(entity.getAge());
		dto.setDob(entity.getDob());
		dto.setEmail(entity.getEmployeeDoPk().getEmail());
		dto.setEmployeeCode(entity.getEmployeeDoPk().getEmployeeCode());
		dto.setGender(entity.getGender());
		dto.setHireDate(entity.getHireDate());
		dto.setMaritalStatus(entity.getMaritalStatus());
		dto.setName(entity.getName());
		dto.setStatus(entity.getStatus());
		return dto;
	}
}
