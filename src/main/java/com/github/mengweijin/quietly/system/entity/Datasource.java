package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@TableName("QTL_DATASOURCE")
public class Datasource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_PROJECT id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * datasource name
     */
    @TableField("NAME")
    private String name;

    /**
     * database type. mysql/h2/oracle/redis/MongoDB etc. Refer to com.baomidou.mybatisplus.annotation.DbType
     */
    @TableField("DB_TYPE")
    private DbType dbType;

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
     * 是否作为默认数据源。Y, N
     */
    @TableField("AS_DEFAULT")
    private String asDefault;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
