package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.enums.StepType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_STEP_DEFINITION
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_STEP_DEFINITION")
public class StepDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_CASE_DEFINITION id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("CASE_ID")
    private Long caseId;

    /**
     * 执行步骤类型。关联枚举类 ${@link StepType}
     */
    @TableField("STEP_TYPE")
    private StepType stepType;

    /**
     * 行为、断言表达式。执行SQL就是具体的SQL语句(支持多个sql以;分割，但只支持存在最多一条查询sql)；断言表达式字符串和JSON断言用不到这个字段；断言表达式JSON-Path和XML-Path 就是其对应的语法。',
     */
    @TableField("EXPRESSION")
    private String expression;

    /**
     * 执行SQL关联的数据库 QTL_ENVIRONMENT_DATASOURCE id.
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("DATASOURCE_ID")
    private Long datasourceId;

    /**
     * 调用接口行为才用得到。关联的表QTL_API_DEFINITION 的 id；
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("API_ID")
    private Long apiId;

    /**
     * 调用接口行为才用得到。固定JSON格式(其中的值可以使用占位符，以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。)
     *
     * {
     *      "token": "${token}"
     * }
     */
    @TableField("API_HEADERS")
    private String apiHeaders;


    /**
     * 调用接口行为才用得到。固定JSON格式(其中的值可以使用占位符，以 ${} 作为占位符来引用上一个执行步骤传递过来的参数。)
     *
     * {
     *      "username": "jack"
     *      "userId": "${userId}"
     * }
     */
    @TableField("API_BODY_ARGS")
    private String apiBodyArgs;

    /**
     * Expect value.
     */
    @TableField("EXPECT_VALUE")
    private String expectValue;

    /**
     * Actual value
     */
    @TableField("ACTUAL_VALUE")
    private String actualValue;

    /**
     * The execution order of steps, generated by the program.
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 最新执行状态。Refer to ${@link CaseStepStatus} enum.
     */
    @TableField("STATUS")
    private CaseStepStatus status;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

    /**
     * 调用接口行为才用得到。记录当前api实际调用时的参数信息。固定JSON格式：
     * {
     *      "url":"http://localhost:8080/user/123",
     *      "headers": {"token": "abcd"},
     *      "requestBody": {}
     * }
     */
    @TableField("API_REQUEST_ACTUAL_INFO")
    private String apiRequestActualInfo;


    /**
     * 执行失败时记录的错误信息。
     */
    @TableField("ERROR_INFO")
    private String errorInfo;

}
