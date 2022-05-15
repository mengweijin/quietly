package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * Base url for current project. E.g.: http://localhost:8080/
     */
    @TableField("BASE_URL")
    private String baseUrl;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
