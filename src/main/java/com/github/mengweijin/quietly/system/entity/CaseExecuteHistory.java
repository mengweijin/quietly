package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_CASE_EXECUTE_HISTORY
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_CASE_EXECUTE_HISTORY")
public class CaseExecuteHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_CASE_DEFINITION id
     */
    @TableField("CASE_ID")
    private Long caseId;

    /**
     * 执行失败时，记录 QTL_STEP_DEFINITION id
     */
    @TableField("ERROR_STEP_ID")
    private Long errorStepId;

    /**
     * 执行失败时，如果当前 step 是断言，就记录一下上一步返回的期望值。
     */
    @TableField("ERROR_EXPECT_VALUE")
    private String errorExpectValue;

    /**
     * 执行失败时，如果当前 step 是断言，就记录一下上一步返回的实际值。
     */
    @TableField("ERROR_ACTUAL_VALUE")
    private String errorActualValue;

    @TableField("ERROR_INFO")
    private String errorInfo;

    /**
     * Refer to com.github.mengweijin.quietly.enums.CaseStatus enum.
     */
    @TableField("STATUS")
    private String status;

}
