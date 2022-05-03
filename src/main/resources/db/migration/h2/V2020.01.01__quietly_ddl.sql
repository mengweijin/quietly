drop table IF EXISTS QTL_PROJECT;
create TABLE QTL_PROJECT (
  id bigint NOT NULL COMMENT 'id',
  name varchar(30) NULL COMMENT 'name',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='project info';



drop table IF EXISTS QTL_PROJECT_ENVIRONMENT;
create TABLE QTL_PROJECT_ENVIRONMENT (
  id bigint NOT NULL COMMENT 'id',
  project_id bigint NOT NULL COMMENT 'QTL_PROJECT id',
  name varchar(10) NOT NULL COMMENT 'environment name。DEV/SIT/UAT/PRE-PROD/PROD',
  base_url varchar(100) NULL COMMENT 'project base url on current environment. For Example: http://127.0.0.1:8080/',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_PROJECT_ENVIRONMENT';



drop table IF EXISTS QTL_ENVIRONMENT_DATASOURCE;
create TABLE QTL_ENVIRONMENT_DATASOURCE (
  id bigint NOT NULL COMMENT 'id',
  environment_id bigint NOT NULL COMMENT 'QTL_PROJECT_ENVIRONMENT id',
  name varchar(50) NOT NULL COMMENT 'datasource name',
  db_type varchar(20) NOT NULL COMMENT 'database type. mysql/h2/oracle/redis/MongoDB etc. Refer to com.baomidou.mybatisplus.annotation.DbType',
  url varchar(100) NOT NULL COMMENT 'jdbc url or others(For example: redis=http://host:port). ',
  username varchar(50) NULL COMMENT 'username',
  password varchar(50) NULL COMMENT 'password',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_ENVIRONMENT_DATASOURCE';



drop table IF EXISTS QTL_API_DEFINITION;
create TABLE QTL_API_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  project_id bigint NOT NULL COMMENT 'QTL_PROJECT id',
  http_method varchar(10) NOT NULL COMMENT 'HTTP request type: GET/POST/PUT/DELETE. Refer to class org.springframework.http.HttpMethod',
  url varchar(500) NULL COMMENT 'api url。以 ${} 作为占位符。如：http://localhost:8080/quietly?username=${username}&status=${status}。如果需要给参数设置默认值，直接在 url 中写死就行。',
  default_body_args varchar NULL COMMENT 'request body 中的参数，以 JSON 格式存储。如：{"username"="admin", "status"="123456"}',
  default_headers varchar NULL COMMENT '请求头中的参数，以 ${} 作为占位符，JSON 格式存储。如：{"contentType"="application/json;charset=UTF-8", "token"="${token}"}',
  name varchar(100) NOT NULL COMMENT 'api name',
  description varchar(500) NULL COMMENT 'api description',
  request_media_type varchar(100) NULL COMMENT '请求数据类型. For example: "application/json;charset=UTF-8". Refer to class org.springframework.http.MediaType',
  response_media_type varchar(100) NULL COMMENT '响应数据类型. For example: "application/json;charset=UTF-8". Refer to class org.springframework.http.MediaType',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_API_DEFINITION';



drop table IF EXISTS QTL_CASE_DEFINITION;
create TABLE QTL_CASE_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  name varchar(100) NOT NULL COMMENT 'name',
  description varchar(500) NOT NULL COMMENT 'description',
  status varchar(20) NOT NULL DEFAULT 'CREATED' COMMENT '最新执行状态。Refer to com.github.mengweijin.quietly.enums.CaseStatus enum.',
  enabled char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y, N',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_CASE_DEFINITION';



drop table IF EXISTS QTL_STEP_DEFINITION;
create TABLE QTL_STEP_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  case_id bigint NOT NULL COMMENT 'QTL_CASE_DEFINITION id',
  step_type_data_id bigint NOT NULL COMMENT '执行步骤类型(表 QTL_DICT_TYPE 的 code=case_step_type 的类型)，关联表 QTL_DICT_DATA 的 id',
  action_expression varchar NOT NULL COMMENT '行为表达式。执行SQL就是具体的SQL语句；调用接口就是关联的表QTL_API_DEFINITION的 id；',
  action_sql_datasource_id bigint NULL COMMENT '执行SQL关联的数据库 QTL_ENVIRONMENT_DATASOURCE id.',
  action_api_url_args varchar NULL COMMENT '调用接口 url 参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。如：http://localhost:8080/quietly?username=${username}。',
  action_api_body_args varchar NULL COMMENT '调用接口请求体参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。可以从表 QTL_API_DEFINITION 中获取 API 默认参数并合并到这里。',
  action_api_headers varchar NULL COMMENT '调用接口请求头参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。可以从表 QTL_API_DEFINITION 中获取 API 默认参数并合并到这里。',
  assert_expression varchar NULL COMMENT '断言表达式字符串和JSON断言用不到这个字段；JSON-Path和XML-Path 就是其对应的语法。',
  assert_expect_value varchar NULL COMMENT 'Expect value.',
  assert_actual_value varchar NULL COMMENT 'Actual value',
  field_1 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  field_2 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  field_3 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  field_4 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  field_5 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  field_6 varchar NULL COMMENT '备用字段。如果用户需要自定义 Step 定义的话，上面这些字段可能不够，所以留几个备用字段。',
  seq int(4) NOT NULL DEFAULT 1 COMMENT 'The execution order of steps, generated by the program.',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_STEP_DEFINITION';



drop table IF EXISTS QTL_CASE_EXECUTE_HISTORY;
create TABLE QTL_CASE_EXECUTE_HISTORY (
  id bigint NOT NULL COMMENT 'id',
  case_id bigint NOT NULL COMMENT 'QTL_CASE_DEFINITION id',
  error_step_id bigint NULL COMMENT '执行失败时，记录 QTL_STEP_DEFINITION id',
  error_expect_value varchar NULL COMMENT '执行失败时，如果当前 step 是断言，就记录一下上一步返回的期望值。',
  error_actual_value varchar NULL COMMENT '执行失败时，如果当前 step 是断言，就记录一下上一步返回的实际值。',
  error_info varchar NULL DEFAULT '执行失败时，记录异常信息。',
  status varchar(20) NOT NULL DEFAULT 'FAILED' COMMENT 'Refer to com.github.mengweijin.quietly.enums.CaseStatus enum.',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_CASE_EXECUTE_HISTORY';
