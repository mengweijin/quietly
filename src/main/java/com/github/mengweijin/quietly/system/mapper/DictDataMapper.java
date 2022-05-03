package com.github.mengweijin.quietly.system.mapper;

import com.github.mengweijin.quietly.system.entity.DictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * QTL_DICT_DATA Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * by dict type code
     * @param code code
     * @return DictData
     */
    List<DictData> selectByDictTypeCode(String code);
}

