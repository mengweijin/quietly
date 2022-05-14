package com.github.mengweijin.quietly.step;

import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.listener.event.StepFailedEvent;
import com.github.mengweijin.quietly.listener.event.StepStartEvent;
import com.github.mengweijin.quietly.listener.event.StepSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
public abstract class AbstractStep {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 支持的 StepType 类型
     * @return StepType
     */
    public abstract StepType stepType();

    /**
     * 子类实现方法
     * @param stepId stepId
     * @param stepArgs stepArgs
     * @return
     */
    public abstract Map<String, Object> invoke(Long stepId, StepArgs stepArgs) throws Exception;

    /**
     * 入口方法
     * @param stepId stepId
     */
    public void run(Long stepId) {
        try{
            applicationEventPublisher.publishEvent(new StepStartEvent(this, stepId));

            // 临时保存 StepArgs
            StepArgs stepArgs = StepContextHolder.get();
            if(stepArgs == null) {
                stepArgs = new StepArgs();
            }

            // 重置 StepContextHolder
            StepContextHolder.clear();

            // 执行逻辑
            Map<String, Object> map = this.invoke(stepId, stepArgs);

            // 设置  StepContextHolder
            StepContextHolder.set(new StepArgs().setData(map));

            applicationEventPublisher.publishEvent(new StepSuccessEvent(this, stepId));
        } catch (Throwable t) {
            applicationEventPublisher.publishEvent(new StepFailedEvent(this, stepId, t));
            throw new QuickBootException(t);
        }
    }
}
