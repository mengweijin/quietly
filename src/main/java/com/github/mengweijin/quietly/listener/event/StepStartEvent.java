package com.github.mengweijin.quietly.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author mengweijin
 * @date 2022/5/14
 */
public class StepStartEvent extends ApplicationEvent {

    @Getter @Setter
    private Long stepId;

    public StepStartEvent(Object source, Long stepId) {
        super(source);
        this.stepId = stepId;
    }
}
