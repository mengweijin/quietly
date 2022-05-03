drop table IF EXISTS QTL_DICT_TYPE;
create TABLE QTL_DICT_TYPE (
  id bigint NOT NULL COMMENT 'id',
  name varchar(50) NOT NULL COMMENT 'dict type name',
  code varchar(50) NOT NULL COMMENT 'dict type code',
  description varchar(500) NULL COMMENT 'description',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id),
  UNIQUE KEY QTL_DICT_TYPE_NAME (name) USING BTREE,
  UNIQUE KEY QTL_DICT_TYPE_CODE (code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_DICT_TYPE';

insert into QTL_DICT_TYPE values (1, '用例步骤类型', 'case_step_type', '用例步骤类型', 0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());



drop table IF EXISTS QTL_DICT_DATA;
create TABLE QTL_DICT_DATA (
  id bigint NOT NULL COMMENT 'id',
  dict_type_id bigint NOT NULL COMMENT 'QTL_DICT_TYPE id',
  name varchar(10) NOT NULL COMMENT 'dict name',
  code varchar(100) NOT NULL COMMENT 'dict code',
  seq int(4) NOT NULL DEFAULT 1 COMMENT 'Sequence Number',
  description varchar(500) NULL COMMENT 'description',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id),
  UNIQUE KEY QTL_DICT_DATA_DICTTYPEID_CODE (dict_type_id, name) USING BTREE,
  UNIQUE KEY QTL_DICT_DATA_DICTTYPEID_CODE (dict_type_id, code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_DICT_DATA';

insert into QTL_DICT_DATA values (1001, 1, '执行 SQL',       'ACTION_EXECUTE_SQL',          1, '执行一条 SQL 语句',    0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_DICT_DATA values (1002, 1, '发送 HTTP 请求', 'ACTION_SEND_HTTP_REQUEST',   2, '发送一个 http 请求',   0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_DICT_DATA values (1011, 1, '字符串',         'ASSERT_STRING',               11, '上一个Action执行后，对其返回的结果进行字符串断言。expectValue.equals(responseActualValue)',                              0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_DICT_DATA values (1012, 1, 'JSON',           'ASSERT_JSON',                12, '上一个Action执行后，对其返回的结果进行JSON断言。断言期待的JSON值和 response 中响应的实际JSON值是否相匹配。',                    0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_DICT_DATA values (1013, 1, 'JSON-Path',      'ASSERT_JSON_PATH',           13, '上一个Action执行后，对其返回的结果进行JSON-Path断言。用户提供一个 JSON-Path 表达式和期待值，以此和 response 响应的值做比对。',   0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_DICT_DATA values (1014, 1, 'XML-Path',       'ASSERT_XML_PATH',             14, '上一个Action执行后，对其返回的结果进行XML-Path断言。用户提供一个 XML-path 表达式和期待值，以此和 response 响应的值做比对。',     0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());



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
  step_type_id bigint NOT NULL COMMENT '执行步骤类型(表 QTL_DICT_TYPE 的 code=case_step_type 的类型)，关联表 QTL_DICT_DATA 的 id',
  action_expression varchar NOT NULL COMMENT '行为表达式。执行SQL就是具体的SQL语句；调用接口就是关联的表QTL_API_DEFINITION的 id；',
  action_api_url_args varchar NULL COMMENT '调用接口 url 参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。如：http://localhost:8080/quietly?username=${username}。',
  action_api_body_args varchar NULL COMMENT '调用接口请求体参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。可以从表 QTL_API_DEFINITION 中获取 API 默认参数并合并到这里。',
  action_api_headers varchar NULL COMMENT '调用接口请求头参数JSON。以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。可以从表 QTL_API_DEFINITION 中获取 API 默认参数并合并到这里。',
  assert_expression varchar NULL COMMENT '断言表达式字符串和JSON断言用不到这个字段；JSON-Path和XML-Path 就是其对应的语法。',
  assert_expect_value varchar NULL COMMENT 'Expect value.',
  assert_actual_value varchar NULL COMMENT 'Actual value',
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
