package com.github.mengweijin.quietly.cucumber;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.mengweijin.quietly.properties.QuietlyProperties;
import io.cucumber.core.cli.Main;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Commandline class: ${@link io.cucumber.core.cli.Main}
 *
 * commandline args: ${@link io.cucumber.core.options.CommandlineOptionsParser} path: /io/cucumber/core/options/USAGE.txt
 *
 * @author mengweijin
 * @date 2022/4/30
 */
public class CucumberRunner {

    /**
     * commandline:
     * java -cp "path/to/each/jar:path/to/compiled/.class/files" io.cucumber.core.cli.Main /path/to/your/feature/files --glue hellocucumber --glue anotherpackage
     *
     * /home/featureFileDirectory --glue com.mengweijin.quietly.cucumber.step --plugin pretty --plugin summary --plugin com.mengweijin.quietly.cucumber.plugin.TesterPlugin
     * OR
     * /home/test.feature --glue com.mengweijin.quietly.cucumber.step --plugin pretty --plugin summary --plugin com.mengweijin.quietly.cucumber.plugin.TesterPlugin
     *
     * @param feature file or directory
     */
    public static void runCase(File feature) {
        List<String> list = new LinkedList<>();
        list.add(feature.getAbsolutePath());
        list.add("--glue com.mengweijin.quietly.cucumber.step");
        list.add("--monochrome");
        list.add("--snippets camelcase");
        list.add("--plugin pretty");
        list.add("--plugin summary");
        list.add("--plugin com.mengweijin.quietly.cucumber.plugin.ApiCasePlugin");

        QuietlyProperties quietlyProperties = SpringUtil.getBean(QuietlyProperties.class);
        if(StrUtil.isNotBlank(quietlyProperties.getStepPackage())) {
            String customerGlue = "--glue " + quietlyProperties.getStepPackage();
            list.add(customerGlue);
        }

        String[] args = list.toArray(list.toArray(new String[0]));
        Main.run(args, Thread.currentThread().getContextClassLoader());
    }
}
