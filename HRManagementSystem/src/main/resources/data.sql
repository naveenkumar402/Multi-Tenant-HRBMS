create table if not exists tenant(
	uuid varchar(50) primary key,
	tenantName varchar(50) unique,
	tenantLocation varchar(50)
);

create table if not exists department(
	uuid varchar(50) primary key,
	departmentName varchar(50),
	tenant varchar(50),
	foreign key (tenant) references tenant(uuid) on delete cascade
 );
 
 
create table if not exists authority(
	uuid varchar(50) primary key,
	roleName varchar(50)
 );
 
 
create table if not exists Employee(
	uuid varchar(50) primary key,
	firstName varchar(50),
	lastName varchar(50),
	email varchar(50),
	mobile numeric(10),
	role varchar(50),
	salary numeric(6),
	username varchar(50),
	password varchar(255),
	authority varchar(50) ,
	foreign key (authority) references authority(uuid) on delete cascade,
	department varchar(50) ,
	foreign key (department) references department(uuid) on delete cascade,
	tenant varchar(50),
	foreign key (tenant) references tenant(uuid) on delete cascade 
	
);


 create table if not exists leaveRequest(
	uuid varchar(50) primary key,
	reason varchar(255),
	fromDate date,
	ToDate date,
	numberOfDays int,
	comments varchar(255),
	raisedBy varchar(50),
	foreign key (raisedBy) references employee(uuid) on delete cascade,
	headApproval boolean,
	hrApproval boolean 
 );
 
create table if not exists auditlog(
	username varchar(50),
	role varchar(50),
	method varchar(50),
	uri varchar(255),
	status int,
	timestamp timestamp
);