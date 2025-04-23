package com.ksv.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.LeaveRequest;
import com.ksv.hrms.entity.Authority;

@Repository
public interface LeaveRequestsDao {

	List<LeaveRequest> getLeaveRequests(Authority role);

	int saveRequest(LeaveRequest leaveRequest);

	LeaveRequest getLeaveRequestById(String id);

	int update(LeaveRequest leaveReq);

}
