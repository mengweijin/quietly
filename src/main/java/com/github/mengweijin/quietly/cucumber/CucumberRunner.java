package com.github.mengweijin.quietly.cucumber;

import io.cucumber.core.cli.Main;

import java.io.File;

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
        String[] args = {
                feature.getAbsolutePath(),
                "--glue com.mengweijin.quietly.cucumber.step",
                "--monochrome",
                "--snippets camelcase",
                "--plugin pretty",
                "--plugin summary",
                "--plugin com.mengweijin.quietly.cucumber.plugin.ApiCasePlugin",
        };

        Main.run(args, Thread.currentThread().getContextClassLoader());
    }
}
