package com.github.mengweijin.quietly.step;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
public class StepContextHolder {

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
