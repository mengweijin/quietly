package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_ENVIRONMENT_DATASOURCE
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_ENVIRONMENT_DATASOURCE")
public class EnvironmentDatasource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_PROJECT_ENVIRONMENT id
     */
    @TableField("ENVIRONMENT_ID")
    private Long environmentId;

    /**
     * datasource name
     */
    @TableField("NAME")
    private String name;

    /**
     * database type. mysql/h2/oracle/redis/MongoDB etc. Refer to com.baomidou.mybatisplus.annotation.DbType
     */
    @TableField("DB_TYPE")
    private String dbType;

    /**
     * jdbc url or others(For example: redis=http://host:port). 
     */
    @TableField("URL")
    private String url;

    /**
     * username
     */
    @TableField("USERNAME")
    private String username;

    /**
     * password
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
