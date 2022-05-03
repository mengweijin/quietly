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
 * QTL_DICT_DATA
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_DICT_DATA")
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_DICT_TYPE id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("DICT_TYPE_ID")
    private Long dictTypeId;

    /**
     * dict name
     */
    @TableField("NAME")
    private String name;

    /**
     * dict code
     */
    @TableField("CODE")
    private String code;

    /**
     * Sequence Number
     */
    @TableField("SEQ")
    private Integer seq;

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
