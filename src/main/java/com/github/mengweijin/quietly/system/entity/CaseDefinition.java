package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_CASE_DEFINITION
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_CASE_DEFINITION")
public class CaseDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_PROJECT id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * name
     */
    @TableField("NAME")
    private String name;

    /**
     * description
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 最新执行状态。Refer to ${@link CaseStepStatus} enum.
     */
    @TableField("STATUS")
    private CaseStepStatus status;

    /**
     * Y, N
     */
    @TableField("ENABLED")
    private String enabled;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
