package com.incture.employee.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.incture.employee.dto.EmployeeStagingDto;
import com.incture.employee.entity.EmployeeStagingDo;
import com.incture.employee.exceptions.InvalidInputFault;
import com.incture.employee.util.ServiceUtil;

@Repository("EmployeeStagingDao")
public class EmployeeStagingDao extends BaseDao<EmployeeStagingDo, EmployeeStagingDto>
		implements EmployeeStagingDaoLocal {

	@Override
	public void saveEmployee(EmployeeStagingDto dto) {
		EmployeeStagingDao entity = (EmployeeStagingDao) getSession().get(EmployeeStagingDao.class, dto.getEmail());
		if (!ServiceUtil.isEmpty(entity)) {
			getSession().merge(importDto(dto));
		} else {
			getSession().persist(importDto(dto));
		}
	}

	@Override
	public EmployeeStagingDo importDto(EmployeeStagingDto dto) {
		EmployeeStagingDo entity = new EmployeeStagingDo();
		entity.setEmail(dto.getEmail());
		entity.setEmployeeCode(dto.getEmployeeCode());
		entity.setAccountNumber(dto.getAccountNumber());
		entity.setAccountType(dto.getAccountType());
		entity.setAge(dto.getAge());
		entity.setBankName(dto.getBankName());
		entity.setCompany(dto.getCompany());
		entity.setDepartment(dto.getDepartment());
		entity.setDesignation(dto.getDesignation());
		entity.setDob(dto.getDob());
		entity.setEmploymentType(dto.getEmploymentType());
		entity.setGender(dto.getGender());
		entity.setHireDate(dto.getHireDate());
		entity.setIdentificationNumber(dto.getIdentificationNumber());
		entity.setIdentificationType(dto.getIdentificationType());
		entity.setLocation(dto.getLocation());
		entity.setMaritalStatus(dto.getMaritalStatus());
		entity.setName(dto.getName());
		entity.setPfCode(dto.getPfCode());
		entity.setRequestId(dto.getRequestId());
		entity.setStatus(dto.getStatus());
		entity.setUpdatedBy(dto.getUpdatedBy());
		entity.setUpdatedOn(dto.getUpdatedOn());
		return entity;
	}

	@Override
	public EmployeeStagingDto exportDto(EmployeeStagingDo entity) {
		EmployeeStagingDto dto = new EmployeeStagingDto();
		dto.setEmail(entity.getEmail());
		dto.setEmployeeCode(entity.getEmployeeCode());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setAccountType(entity.getAccountType());
		dto.setAge(entity.getAge());
		dto.setBankName(entity.getBankName());
		dto.setCompany(entity.getCompany());
		dto.setDepartment(entity.getDepartment());
		dto.setDesignation(entity.getDesignation());
		dto.setDob(entity.getDob());
		dto.setEmploymentType(entity.getEmploymentType());
		dto.setGender(entity.getGender());
		dto.setHireDate(entity.getHireDate());
		dto.setIdentificationNumber(entity.getIdentificationNumber());
		dto.setIdentificationType(entity.getIdentificationType());
		dto.setLocation(entity.getLocation());
		dto.setMaritalStatus(entity.getMaritalStatus());
		dto.setName(entity.getName());
		dto.setPfCode(entity.getPfCode());
		dto.setRequestId(entity.getRequestId());
		dto.setStatus(entity.getStatus());
		dto.setUpdatedBy(entity.getUpdatedBy());
		dto.setUpdatedOn(entity.getUpdatedOn());
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeStagingDto> fetchEmployeeStagingData(int reqId) {
		List<EmployeeStagingDto> list = new ArrayList<EmployeeStagingDto>();
		Query query = getSession().createQuery("Select e from EmployeeStagingDo e where e.requestId = :requestId");
		query.setParameter("requestId", reqId);
		List<EmployeeStagingDo> employeeStagingData = (List<EmployeeStagingDo>) query.getResultList();
		if (!ServiceUtil.isEmpty(employeeStagingData)) {
			for (EmployeeStagingDo entity : employeeStagingData) {
				list.add(exportDto(entity));
			}
		}
		return list;
	}

	@Override
	public void deleteEmployeeStagingData(int reqId) {
		Query query = getSession().createQuery("Delete from EmployeeStagingDo e where e.requestId = :requestId");
		query.setParameter("requestId", reqId);
		query.executeUpdate();

	}

	@Transactional
	@Override
	public void updateEmployeeStatus(EmployeeStagingDto dto) throws InvalidInputFault {
		EmployeeStagingDo entity = (EmployeeStagingDo) getSession().get(EmployeeStagingDo.class, dto.getEmail());
		if (!ServiceUtil.isEmpty(entity)) {
			entity.setStatus(dto.getStatus());
			getSession().merge(entity);
		} else {
			throw new InvalidInputFault("Employee does not exist!");
		}
	}

}
