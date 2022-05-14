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
 * project info
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_PROJECT")
public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * name
     */
    @TableField("NAME")
    private String name;

    /**
     * default_datasource_id
     * 默认数据源 ID，如果 QTL_STEP_DEFINITION 中没有设置 datasource，则从这里获取。
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("DEFAULT_DATASOURCE_ID")
    private Long defaultDatasourceId;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
