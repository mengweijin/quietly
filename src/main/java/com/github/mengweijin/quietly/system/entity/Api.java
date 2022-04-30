package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * application program interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_API")
public class Api extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * test api url
     */
    @TableField("URL")
    private String url;

    /**
     * HTTP request type: GET/POST/PUT/DELETE
     */
    @TableField("REQUEST_TYPE")
    private String requestType;

    /**
     * get token url
     */
    @TableField("LOGIN_URL")
    private String loginUrl;

    /**
     * HTTP request type: GET/POST
     */
    @TableField("LOGIN_REQUEST_TYPE")
    private String loginRequestType;

    /**
     * get token args json
     */
    @TableField("LOGIN_ARGS")
    private String loginArgs;

}
