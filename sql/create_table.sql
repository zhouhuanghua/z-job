CREATE TABLE `z_job_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '应用名称',
  `app_desc` varchar(128) NOT NULL COMMENT '应用描述',
  `creator` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_way` tinyint(1) NOT NULL COMMENT '创建方式：1-自动，2-手工',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `address_list` varchar(512) NOT NULL COMMENT '应用地址列表，多个逗号分隔',
  `enabled` tinyint(1) NOT NULL COMMENT '启用状态：1-启用，0-停用',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否删除：1-是，0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_name` (`app_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `z_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_app_id` int(11) NOT NULL COMMENT '任务所属应用id',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_desc` varchar(512) DEFAULT '' COMMENT '任务描述',
  `alarm_email` varchar(512) DEFAULT '' COMMENT '报警邮件，多个逗号分隔',
  `creator` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_way` tinyint(1) NOT NULL COMMENT '创建方式：1-自动，2-手工',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `run_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `run_strategy` tinyint(1) NOT NULL COMMENT '任务执行策略：1-随机，2-轮询',
  `run_param` varchar(512) DEFAULT '' COMMENT '任务执行参数',
  `run_timeout` smallint(3) NOT NULL COMMENT '任务执行超时时间，单位秒',
  `run_fail_retry_count` smallint(3) NOT NULL COMMENT '任务执行失败重试次数',
  `trigger_last_time` datetime DEFAULT NULL COMMENT '上次调度时间',
  `trigger_next_time` datetime DEFAULT NULL COMMENT '下次调度时间',
  `enabled` tinyint(1) NOT NULL COMMENT '启用状态：1-启用，0-停用',
  `is_deleted` tinyint(1) NOT NULL COMMENT '是否删除：1-是，0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_appid` (`job_name`,`job_app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `z_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务ID',
  `run_address_list` varchar(512) NOT NULL COMMENT '本次运行的地址',
  `run_fail_retry_count` smallint(3) NOT NULL COMMENT '任务执行失败重试次数',
  `trigger_start_time` datetime NOT NULL COMMENT '调度开始时间',
  `trigger_end_time` datetime NOT NULL COMMENT '调度结束时间',
  `trigger_result` tinyint(1) NOT NULL COMMENT '调度结果：1-成功，0-失败',
  `trigger_msg` varchar(3000) DEFAULT '' COMMENT '调度日志',
  `job_run_result` tinyint(1) DEFAULT '0' COMMENT '任务执行结果：1-成功，0-失败',
  `job_run_msg` varchar(3000) DEFAULT '' COMMENT '任务执行日志',
  PRIMARY KEY (`id`),
  KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;