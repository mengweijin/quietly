package com.github.mengweijin.quietly.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quietly.enums.ResponseDataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

/**
 * <p>
 * QTL_API_DEFINITION
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QTL_API_DEFINITION")
public class ApiDefinition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * QTL_PROJECT id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("PROJECT_ID")
    private Long projectId;

    /**
     * HTTP request type: GET/POST/PUT/DELETE. Refer to class org.springframework.http.HttpMethod
     */
    @TableField("HTTP_METHOD")
    private HttpMethod httpMethod;

    /**
     * api url。以 ${} 作为占位符。如：http://localhost:8080/quietly?username=${username}&status=${status}。如果需要给参数设置默认值，直接在 url 中写死就行。
     */
    @TableField("URL")
    private String url;

    /**
     * 请求头中的参数，以 ${} 作为占位符，JSON 格式存储。如：{\"contentType\"=\"application/json;charset=UTF-8\", \"token\"=\"${token}\"}
     */
    @TableField("HEADERS_SAMPLE")
    private String headersSample;

    /**
     * request body 中的参数，以 JSON 格式存储。如：{\"username\"=\"admin\", \"status\"=\"123456\"}
     */
    @TableField("BODY_ARGS_SAMPLE")
    private String bodyArgsSample;

    /**
     * api name
     */
    @TableField("NAME")
    private String name;

    /**
     * api description
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 请求数据类型. For example: "application/json;charset=UTF-8". Refer to class ${@link org.springframework.http.MediaType}
     */
    @TableField("REQUEST_MEDIA_TYPE")
    private String requestMediaType;

    /**
     * 响应数据类型. Refer to ${@link com.github.mengweijin.quietly.enums.ResponseDataType}
     */
    @TableField("RESPONSE_DATA_TYPE")
    private ResponseDataType responseDataType;

    /**
     * 逻辑删除。0：未删除；1：已删除；
     */
    @TableField("DELETED")
    private Integer deleted;

}
