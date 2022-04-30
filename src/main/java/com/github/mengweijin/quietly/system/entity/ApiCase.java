package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quietly.cucumber.enums.CaseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * test case
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_API_CASE")
public class ApiCase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * test case name
     */
    @TableField("CASE_NAME")
    private String caseName;

    /**
     * test case description
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * The table QTL_API id
     */
    @TableField("API_ID")
    private Long apiId;

    /**
     * url args JSON string
     */
    @TableField("URL_ARGS")
    private String urlArgs;

    /**
     * request body args JSON string
     */
    @TableField("REQUEST_ARGS")
    private String requestArgs;

    /**
     * CaseStatus enum.
     */
    @TableField("STATUS")
    private CaseStatus status;

    /**
     * feature file content
     */
    @TableField("FEATURE_CONTENT")
    private String featureContent;

    /**
     * test case failed exception message.
     */
    @TableField("EXCEPTION")
    private String exception;

}
