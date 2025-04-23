package com.ksv.hrms.daoimplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ksv.hrms.dao.DepartmentDao;
import com.ksv.hrms.entity.Department;
import com.ksv.hrms.entity.Employee;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.utility.DepartmentMapper;

@Component
public class DepartmentDaoImpln implements DepartmentDao{
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	@Override
	public int save(Department department) {
		String sql="insert into department (uuid,departmentName,tenant) values (:uuid,:departmentName, :tenant)";
		Map<String,Object> param=new HashMap<>();
		param.put("uuid", UUID.randomUUID().toString());
		param.put("departmentName", department.getDepartmentName());
		param.put("tenant",department.getTenant());
		return namedParameterJdbcTemplate.update(sql, param);
	}

	@Override
	public boolean isPresent(Department department) {
		String sql="select count(*) from department where departmentName like :name and tenant= :tenant";
		Map<String, Object> param=new HashMap<>();
		param.put("name", department.getDepartmentName());
		param.put("tenant", department.getTenant());
		int count=namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
		if(count==0) {
			return false;
		}
		return true;
	}

	@Override
	public int getDepartmentId(Department department) {
		String sql="select id from department where departmentName like :name and tenant=:tenant";
		Map<String, Object> param=new HashMap<>();
		param.put("name", department.getDepartmentName());
		param.put("tenant", department.getTenant());
		int id=namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
		return id;
	}

	@Override
	public Department getDepartmentById(String uuid) {
		String sql="select * from department where uuid= :uuid";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", uuid);
		try {
			return namedParameterJdbcTemplate.queryForObject(sql, param, departmentMapper);
		}
		catch(EmptyResultDataAccessException e) {
			throw new NoDataFoundException("No department with this id "+uuid);
		}
	}

	@Override
	public int update(Department department) {
		String sql="update department set departmentName= :name where id= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("name", department.getDepartmentName());
		param.put("id", department.getUuid());
		return namedParameterJdbcTemplate.update(sql, param);
	}

	@Override
	public List<Department> getAllDepartments(Employee authemployee) {
		String sql="";
		Map<String,Object> param=new HashMap<>();
		if(authemployee.getTenant()!=null) {
			sql="select * from department where tenant= :tenant";
			param.put("tenant", authemployee.getTenant());
		}
		else
			sql="select * from department";
		return namedParameterJdbcTemplate.query(sql,param,departmentMapper);
	}

	@Override
	public int delete(String uuid) {
		String sql="delete from department where id= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("id", uuid);
		return namedParameterJdbcTemplate.update(sql, param);
	}

	
	
}
