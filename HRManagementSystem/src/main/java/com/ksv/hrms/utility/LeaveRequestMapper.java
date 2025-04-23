package com.ksv.hrms.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.LeaveRequest;
@Component
public class LeaveRequestMapper implements RowMapper<LeaveRequest>{

	@Override
	public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		LeaveRequest leave=new LeaveRequest();
		leave.setUuid(rs.getString("uuid"));
		leave.setReason(rs.getString("reason"));
		leave.setComments(rs.getString("comments"));
		leave.setEmployee(rs.getString("raisedBy"));
		leave.setFromDate(rs.getDate("fromDate").toLocalDate());
		leave.setToDate(rs.getDate("ToDate").toLocalDate());
		leave.setNumerOfDays(rs.getInt("numberOfDays"));
		leave.setHeadapproval(rs.getBoolean("headApproval"));
		leave.setHrApproval(rs.getBoolean("hrApproval"));
		return leave;
	}
	
}
