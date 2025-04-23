package com.ksv.hrms.daoimplementation;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ksv.hrms.dao.LeaveRequestsDao;
import com.ksv.hrms.entity.LeaveRequest;
import com.ksv.hrms.entity.Authority;
import com.ksv.hrms.exception.NoDataFoundException;
import com.ksv.hrms.utility.LeaveRequestMapper;

@Component
public class LeaveReqDaoImpln implements LeaveRequestsDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private LeaveRequestMapper leaveRequestMapper;
	@Override
	public List<LeaveRequest> getLeaveRequests(Authority role) {
		String sql="";
		if(role.getRoleName().equalsIgnoreCase("hr")) {
			sql="select * from leaveRequest where hrApproval=false and headApproval=true";
		}
		else if(role.getRoleName().equalsIgnoreCase("head")) {
			sql="select * from leaveRequest where headApproval=false";
		}
		return namedParameterJdbcTemplate.query(sql,leaveRequestMapper);
	}
	@Override
	public int saveRequest(LeaveRequest leaveRequest) {
		String sql="insert into leaverequest (uuid,reason,fromDate,ToDate,comments,numberOfDays,raisedBy,headApproval,hrApproval)"
				+ "values (:uuid, :reason, :from, :to, :comments, :days, :raisedBy, :headApproval, :hrApproval)";
		Map<String, Object> param=new HashMap<>();
		param.put("uuid", UUID.randomUUID().toString());
		param.put("reason", leaveRequest.getReason());
		param.put("from", Date.valueOf(leaveRequest.getFromDate()));
		param.put("to", Date.valueOf(leaveRequest.getToDate()));
		param.put("comments", leaveRequest.getComments());
		param.put("days", leaveRequest.getNumerOfDays());
		param.put("raisedBy", leaveRequest.getEmployee());
		param.put("headApproval",leaveRequest.isHeadapproval());
		param.put("hrApproval", leaveRequest.isHrApproval());
		return namedParameterJdbcTemplate.update(sql, param);
	}
	@Override
	public LeaveRequest getLeaveRequestById(String id) {
		String sql="select * from leaverequest where uuid= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("id", id);
		try {
			
			return namedParameterJdbcTemplate.queryForObject(sql, param, leaveRequestMapper);
		}catch (EmptyResultDataAccessException e) {
			throw new NoDataFoundException("There is no leave request with this request Id");
		}
	}
	@Override
	public int update(LeaveRequest leaveReq) {
		String sql="update leaverequest set hrApproval =:hr, headApproval= :head where uuid= :id";
		Map<String, Object> param=new HashMap<>();
		param.put("hr", leaveReq.isHrApproval());
		param.put("head", leaveReq.isHeadapproval());
		param.put("id", leaveReq.getUuid());
		return namedParameterJdbcTemplate.update(sql, param);
	}

}
