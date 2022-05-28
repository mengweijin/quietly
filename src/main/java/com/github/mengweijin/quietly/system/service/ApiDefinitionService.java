package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.system.entity.ApiDefinition;
import com.github.mengweijin.quietly.system.mapper.ApiDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_API_DEFINITION implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class ApiDefinitionService extends ServiceImpl<ApiDefinitionMapper, ApiDefinition> implements IService<ApiDefinition> {

    /**
     * <p>
     * ApiDefinitionMapper
     * </p>
     */
    @Autowired
    private ApiDefinitionMapper apiDefinitionMapper;

    public String[] getMediaTypes() {
        return new String[]{
                MediaType.ALL_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_UTF8_VALUE,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_XHTML_XML_VALUE,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                MediaType.TEXT_PLAIN_VALUE,
                MediaType.TEXT_XML_VALUE,
                MediaType.TEXT_HTML_VALUE,
                MediaType.TEXT_MARKDOWN_VALUE,
                MediaType.APPLICATION_PDF_VALUE,
                MediaType.IMAGE_GIF_VALUE,
                MediaType.IMAGE_JPEG_VALUE,
                MediaType.IMAGE_PNG_VALUE,
                MediaType.APPLICATION_ATOM_XML_VALUE,
                MediaType.APPLICATION_CBOR_VALUE,
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                MediaType.APPLICATION_PROBLEM_XML_VALUE,
                MediaType.APPLICATION_RSS_XML_VALUE,
                MediaType.APPLICATION_NDJSON_VALUE,
                MediaType.MULTIPART_MIXED_VALUE,
                MediaType.MULTIPART_RELATED_VALUE,
                MediaType.TEXT_EVENT_STREAM_VALUE,
        };
    }
}

