package com.incture.employee.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.incture.employee.dto.LookUpDto;
import com.incture.employee.entity.LookUpDo;
import com.incture.employee.util.ServiceUtil;

@Repository("LookUpDao")
public class LookUpDao extends BaseDao<LookUpDo, LookUpDto> implements LookUpDaoLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<LookUpDto> getEmployeeLookUp() {
		List<LookUpDto> dtolist = new ArrayList<LookUpDto>();
		Query query = getSession().createQuery("Select l from LookUpDo l");
		List<LookUpDo> list = (List<LookUpDo>) query.getResultList();
		if (!ServiceUtil.isEmpty(list)) {
			for (LookUpDo entity : list) {
				dtolist.add(exportDto(entity));
			}
		}
		return dtolist;
	}

	@Override
	public void createNewLookUp(LookUpDto lookUpDto) {
		getSession().persist(importDto(lookUpDto));
	}

	@Override
	public LookUpDo importDto(LookUpDto dto) {
		LookUpDo entity = new LookUpDo();
		entity.setLookUp(dto.getLookUp());
		entity.setLookUpId(dto.getLookUpId());
		entity.setLookUpValue(dto.getLookUpValue());
		return entity;
	}

	@Override
	public LookUpDto exportDto(LookUpDo entity) {
		LookUpDto dto = new LookUpDto();
		dto.setLookUp(entity.getLookUp());
		dto.setLookUpId(entity.getLookUpId());
		dto.setLookUpValue(entity.getLookUpValue());
		return dto;
	}

}
