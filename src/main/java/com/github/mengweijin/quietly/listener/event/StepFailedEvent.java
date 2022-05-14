package com.github.mengweijin.quietly.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author mengweijin
 * @date 2022/5/14
 */
public class StepFailedEvent extends ApplicationEvent {

    @Getter @Setter
    private Long stepId;

    @Getter @Setter
    private Throwable throwable;
    public StepFailedEvent(Object source, Long stepId, Throwable throwable) {
        super(source);
        this.stepId = stepId;
        this.throwable = throwable;
    }
}
