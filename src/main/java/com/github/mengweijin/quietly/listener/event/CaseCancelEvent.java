package com.github.mengweijin.quietly.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author mengweijin
 * @date 2022/5/14
 */
public class CaseCancelEvent extends ApplicationEvent {

    @Getter @Setter
    private Long caseId;

    public CaseCancelEvent(Object source, Long caseId) {
        super(source);
        this.caseId = caseId;
    }
}
