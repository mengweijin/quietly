package com.github.mengweijin.quietly.system.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.exception.QuickBootException;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.quietly.system.entity.CaseDefinition;
import com.github.mengweijin.quietly.system.entity.Datasource;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.CaseDefinitionMapper;
import com.github.mengweijin.quietly.system.mapper.DatasourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * QTL_ENVIRONMENT_DATASOURCE implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class DatasourceService extends ServiceImpl<DatasourceMapper, Datasource> implements IService<Datasource> {

    /**
     * <p>
     * EnvironmentDatasourceMapper
     * </p>
     */
    @Autowired
    private DatasourceMapper datasourceMapper;

    @Autowired
    private CaseDefinitionMapper caseDefinitionMapper;

    public Datasource getByStepDefinition(StepDefinition stepDefinition) {
        Datasource ds;
        Long datasourceId = stepDefinition.getDatasourceId();
        if(datasourceId == null) {
            CaseDefinition caseDefinition = caseDefinitionMapper.selectById(stepDefinition.getCaseId());
            ds = this.getDefaultDatasourceByProjectId(caseDefinition.getProjectId());
        } else {
            ds = this.getById(datasourceId);
        }
        // check datasource
        if(ds == null) {
            String msg = "No datasource was found for stepId = " + stepDefinition.getId() +
                    ". Please set a default datasource for this project. Or select a datasource for this test case step.";
            throw new QuickBootException(msg);
        }

        return ds;
    }

    public Datasource getDefaultDatasourceByProjectId(Long projectId) {
        List<Datasource> datasourceList = this.lambdaQuery()
                .eq(Datasource::getProjectId, projectId)
                .eq(Datasource::getAsDefault, Const.Y)
                .list();
        if(CollUtil.isEmpty(datasourceList)) {
            return null;
        } else if (datasourceList.size() > 1) {
            throw new QuickBootException("Multiple default datasource configurations were found, please check your datasource configuration.");
        }
        return datasourceList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setAsDefault(Long id, Long projectId) {
        this.lambdaUpdate().set(Datasource::getAsDefault, Const.N)
                .eq(Datasource::getProjectId, projectId).eq(Datasource::getAsDefault, Const.Y)
                .update();
        this.lambdaUpdate().set(Datasource::getAsDefault, Const.Y)
                .eq(Datasource::getId, id)
                .update();
    }
}

