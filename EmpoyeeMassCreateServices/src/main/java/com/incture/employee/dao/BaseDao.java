package com.incture.employee.dao;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.employee.dto.BaseDto;
import com.incture.employee.entity.BaseDo;
import com.incture.employee.exceptions.ExecutionFault;

@Repository("BaseDao")
public abstract class BaseDao<E extends BaseDo, D extends BaseDto> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	public abstract E importDto(D dto);

	public abstract D exportDto(E entity);

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {

			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
			return sessionFactory.openSession();
		}
	}

	public Session getStatelessSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {

			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
			return sessionFactory.openSession();
		}
	}

	public void persist(E entity) {
		try {
			getSession().persist(entity);
		} catch (Exception e) {
			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
		}
	}

	public String save(E entity) {
		String primaryKey = null;
		try {
			primaryKey = (String) getSession().save(entity);
		} catch (Exception e) {
			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
		}

		return primaryKey;
	}

	public void update(E entity) {
		try {
			getSession().update(entity);
		} catch (Exception e) {
			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
		}
	}

	public void delete(E entity) {
		try {
			getSession().delete(entity);

		} catch (Exception e) {
			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public E get(E entity) {
		E result = null;
		try {
			Long primaryKey = (Long) entity.getPrimaryKey();
			result = (E) getSession().load(entity.getClass(), primaryKey);

		} catch (Exception e) {
			// System.err.println("[BaseDao]" + e.getMessage());
			LOGGER.error("[BaseDao]" + e.getMessage());
		}
		return result;
	}

	/**
	 * Batch create or update
	 *
	 * @param entitys
	 *            the entities
	 * @throws ExecutionFault
	 *             the entity execution fail exception
	 */
	public void batchSaveOrUpdate(Collection<E> entities) throws ExecutionFault {
		if (CollectionUtils.isEmpty(entities)) {
			return;
		}
		try {
			int count = 0;
			for (E entity : entities) {
				count++;

				this.getSession().saveOrUpdate(entity);

				if (count > 0 && count % 50 == 0) {
					getSession().flush();
					getSession().clear();
				}
			}

		} catch (Exception ex) {
			throw new ExecutionFault("Error occoured while creating the record");
		}
	}

	/**
	 * Generate where condition with in operator
	 * 
	 * @param conditions
	 * @return where cluase
	 */
	public String formWhereClause(Collection<String> conditions) {
		if (CollectionUtils.isEmpty(conditions)) {
			return null;
		}
		StringBuilder condition = new StringBuilder("(");
		for (String data : conditions) {
			condition.append("'" + data + "', ");
		}
		condition.deleteCharAt(condition.length() - 2);
		condition.append(")");
		return condition.toString();
	}

	/**
	 * Generate where condition with in operator
	 * 
	 * @param conditions
	 * @return where cluase
	 */
	public String formWhereClause(String[] conditions) {
		if (conditions == null) {
			return null;
		}
		StringBuilder condition = new StringBuilder("(");
		for (String data : conditions) {
			condition.append("'" + data + "', ");
		}
		condition.deleteCharAt(condition.length() - 2);
		condition.append(")");
		return condition.toString();
	}

}
