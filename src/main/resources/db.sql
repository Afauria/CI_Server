create database if not exists db_ci character set utf8 collate utf8_general_ci;
use db_ci;

create table if not exists t_module(
	module_id INT primary key auto_increment,
	name VARCHAR(30) NOT NULL UNIQUE,
	repo VARCHAR(70) NOT NULL,
	branch VARCHAR(30) NOT NULL,
	cur_version VARCHAR(10),
	build_status INT(1) DEFAULT 1,
	#desc是关键字，不能做字段名
	descr VARCHAR(100),
	gmt_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	#只能有一个DEFAULT CURRENT_TIMESTAMP除非加上后面的ON UPDATE CURRENT_TIMESTAMP
	#mysql会默认为表中的第一个timestamp字段（且设置了NOT NULL）隐式设置DEFAULAT CURRENT_TIMESTAMP
	gmt_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)DEFAULT CHARSET=utf8;
results: 10
page: 1
sortField: name
sortOrder: ascend
create table if not exists t_project(
	project_id INT primary key auto_increment,
	name VARCHAR(30) not null,
	repo VARCHAR(50) not null,
	branch VARCHAR(30) not null,
	cur_version VARCHAR(10),
	status INT(1),
	descr VARCHAR(100),
	gmt_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	gmt_update TIMESTAMP NULL
)DEFAULT CHARSET=utf8;

create table if not exists t_project_module(
	id INT primary key auto_increment,
	project_id int,
	module_id int,
	gmt_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	gmt_update TIMESTAMP NULL,
       	FOREIGN KEY (project_id) REFERENCES t_project(project_id),
       	FOREIGN KEY (module_id) REFERENCES t_module(module_id)
)DEFAULT CHARSET=utf8;