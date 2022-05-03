package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QTL_DICT_TYPE
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_DICT_TYPE")
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * dict type name
     */
    @TableField("NAME")
    private String name;

    /**
     * dict type code
     */
    @TableField("CODE")
    private String code;

    /**
     * description
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
