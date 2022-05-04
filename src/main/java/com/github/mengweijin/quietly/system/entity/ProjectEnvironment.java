package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_PROJECT_ENVIRONMENT
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_PROJECT_ENVIRONMENT")
public class ProjectEnvironment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_PROJECT id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * environment name。DEV/SIT/UAT/PRE-PROD/PROD
     */
    @TableField("NAME")
    private String name;

    /**
     * project base url on current environment. For Example: http://127.0.0.1:8080/
     */
    @TableField("BASE_URL")
    private String baseUrl;

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
