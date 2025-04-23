package com.ksv.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksv.hrms.entity.AuditLog;

@Repository
public interface AuditlogDao {
	int save(AuditLog auditLog);

	List<AuditLog> getAuditlog();
}
