package com.github.mengweijin.quietly.cucumber;

/**
 * data source session
 * @author mengweijin
 */
public class ScenarioThreadLocal {

    private static final ThreadLocal<StepArgs> threadLocal = new ThreadLocal<>();

    public static void set(StepArgs stepArgs) {
        threadLocal.set(stepArgs);
    }

    public static StepArgs get() {
        return threadLocal.get();
    }

    public static void clear(){
        threadLocal.remove();
    }
}
