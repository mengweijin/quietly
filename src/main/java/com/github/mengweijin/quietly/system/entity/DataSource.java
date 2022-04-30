package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Data source info
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_DATA_SOURCE")
public class DataSource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * jdbc url
     */
    @TableField("URL")
    private String url;

    /**
     * Database username
     */
    @TableField("USERNAME")
    private String username;

    /**
     * Database password
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * Logic delete,（0 no delete; 1 deleted）
     */
    @TableField("DELETED")
    private Integer deleted;

}
