drop table IF EXISTS QTL_PROJECT;
create TABLE QTL_PROJECT (
  id bigint NOT NULL COMMENT 'id',
  name varchar(30) NULL COMMENT 'name',
  base_url varchar(100) NULL COMMENT 'Base url for current project. E.g.:http://localhost:8080/',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='project info';

insert into QTL_PROJECT values (1, 'Quietly', 'http://localhost:8080/', 0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());



drop table IF EXISTS QTL_DATASOURCE;
create TABLE QTL_DATASOURCE (
  id bigint NOT NULL COMMENT 'id',
  project_id bigint NOT NULL COMMENT 'QTL_PROJECT id',
  name varchar(50) NOT NULL COMMENT 'datasource name',
  db_type varchar(20) NOT NULL COMMENT 'database type. mysql/h2/oracle/redis/MongoDB etc. Refer to ${@link com.baomidou.mybatisplus.annotation.DbType}',
  url varchar(100) NOT NULL COMMENT 'jdbc url or others(For example: redis=http://host:port). ',
  username varchar(50) NULL COMMENT 'username',
  password varchar(50) NULL COMMENT 'password',
  as_default char(1) NOT NULL DEFAULT 'N' COMMENT '是否作为默认数据源。Y, N',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_DATASOURCE';

insert into QTL_DATASOURCE values (1, 1, 'Quietly', 'H2', 'jdbc:h2:file:./h2/quickboot;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL', 'sa', null, 'Y', 0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());



drop table IF EXISTS QTL_API_DEFINITION;
create TABLE QTL_API_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  project_id bigint NOT NULL COMMENT 'QTL_PROJECT id',
  http_method varchar(10) NOT NULL COMMENT 'HTTP request type: GET/POST/PUT/DELETE. Refer to ${@link org.springframework.http.HttpMethod}',
  url varchar(500) NULL COMMENT 'api url。以 ${} 作为占位符。如：http://localhost:8080/quietly?username=${username}&status=${status}。如果需要给参数设置默认值，直接在 url 中写死就行。',
  headers_sample varchar NULL COMMENT '请求头中的参数，以 ${} 作为占位符，JSON 格式存储。如：{"contentType"="application/json;charset=UTF-8", "token"="${token}"}',
  body_args_sample varchar NULL COMMENT 'request body 中的参数，以 JSON 格式存储。如：{"username"="admin", "status"="Y"}',
  name varchar(100) NOT NULL COMMENT 'api name',
  description varchar(500) NULL COMMENT 'api description',
  request_media_type varchar(100) NULL DEFAULT 'application/json' COMMENT '请求media类型. For example: "application/json". Refer to ${@link class org.springframework.http.MediaType}',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_API_DEFINITION';

insert into QTL_API_DEFINITION values (1, 1, 'GET', '/step-definition/getStepTypes', '{"token": "${token}"}', '{"username": "admin", "status": "Y"}', '获取系统 step 定义集合', '获取步骤定义列表', 'application/json', 0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());



drop table IF EXISTS QTL_CASE_DEFINITION;
create TABLE QTL_CASE_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  project_id bigint NOT NULL COMMENT 'QTL_PROJECT id',
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

insert into QTL_CASE_DEFINITION values (1, 1, '获取步骤定义列表接口测试', '获取步骤定义列表-成功', 'CREATED', 'Y', 0, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());


drop table IF EXISTS QTL_STEP_DEFINITION;
create TABLE QTL_STEP_DEFINITION (
  id bigint NOT NULL COMMENT 'id',
  case_id bigint NOT NULL COMMENT 'QTL_CASE_DEFINITION id',
  step_type varchar(100) NOT NULL COMMENT '执行步骤类型。关联枚举类 com.github.mengweijin.quietly.enums.StepType',
  expression varchar NULL COMMENT '行为、断言表达式。执行SQL就是具体的SQL语句(支持多个sql以;分割，但只支持存在最多一条查询sql)；断言表达式字符串和JSON断言用不到这个字段；断言表达式JSON-Path和XML-Path 就是其对应的语法。',
  datasource_id bigint NULL COMMENT '执行SQL关联的数据库 QTL_DATASOURCE id.',
  api_id bigint NULL COMMENT '调用接口行为才用得到。关联的表QTL_API_DEFINITION 的 id；',
  api_args varchar NULL COMMENT '调用接口行为才用得到。固定JSON格式(其中的值可以使用占位符，以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。)：{"headers": {"token": "${token}"}, "requestBody": {}}',
  expect_value varchar NULL COMMENT '断言才用得到。Expect value.',
  actual_value varchar NULL COMMENT '断言才用得到。Actual value',
  seq int(4) NOT NULL DEFAULT 0 COMMENT 'The execution order of steps, generated by the program.',
  status varchar(20) NOT NULL DEFAULT 'CREATED' COMMENT 'Refer to ${@link com.github.mengweijin.quietly.enums.CaseStatus}',
  deleted int NOT NULL DEFAULT 0 COMMENT '逻辑删除。0：未删除；1：已删除；',
  api_request_actual_info varchar NULL COMMENT '调用接口行为才用得到。记录当前api实际调用时的参数信息。固定JSON格式：{"url":"http://localhost:8080/user/123", "headers": {"token": "abcd"}, "requestBody": {}}',
  error_info varchar NULL COMMENT '执行失败时记录的错误信息。',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QTL_STEP_DEFINITION';


insert into QTL_STEP_DEFINITION values(1, 1, 'ACTION_CALL_API', null, null, 1, null, null, null, 1, 'CREATED', 0, '{"url":"http://localhost:8080/step-definition/getStepList", "headers": {"Content-Type": "application/json;charset=UTF-8"}, "requestBody": {}}', null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(2, 1, 'ASSERT_API_RESPONSE_HTTP_CODE', null, null, null, null, '200', null, 2, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(3, 1, 'ASSERT_API_RESPONSE_BY_JSON_PATH', '$[0].key', null, null, null, 'ACTION_CALL_API', null, 3, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(4, 1, 'ASSERT_API_RESPONSE_BY_JSON_PATH', '$[0].name', null, null, null, '动作-调用接口', null, 4, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(5, 1, 'ASSERT_DB_BY_QUERY_SQL', 'SELECT http_method FROM QTL_API_DEFINITION where id=1;', null, null, null, 'GET', null, 5, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(6, 1, 'ASSERT_DB_BY_QUERY_SQL', 'SELECT project_id, http_method FROM QTL_API_DEFINITION where id=1;', null, null, null, '{"project_id": 1, "HTTP_METHOD": "GET"}', null, 6, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
insert into QTL_STEP_DEFINITION values(7, 1, 'ASSERT_DB_BY_QUERY_SQL', 'SELECT project_id, http_method FROM QTL_API_DEFINITION where id=1;', null, null, null, '[{"project_id": 1, "http_method": "GET"}]', null, 7, 'CREATED', 0, null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());
