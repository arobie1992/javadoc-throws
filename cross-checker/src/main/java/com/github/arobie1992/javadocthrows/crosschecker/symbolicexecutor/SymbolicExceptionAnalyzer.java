package com.github.arobie1992.javadocthrows.crosschecker.symbolicexecutor;

import com.github.arobie1992.javadocthrows.crosschecker.analysis.AnalysisProperties;
import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.OriginMethod;
import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.Parameter;
import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.SymbolicExecutionExceptionInformation;
import jbse.apps.run.Run;
import jbse.apps.run.RunParameters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static jbse.apps.run.RunParameters.DecisionProcedureType.Z3;
import static jbse.apps.run.RunParameters.StateFormatMode.TEXT;
import static jbse.apps.run.RunParameters.StepShowMode.LEAVES;

@Component
public class SymbolicExceptionAnalyzer {

    private static final String PROJECT_ROOT = SymbolicExceptionAnalyzer.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            .replace("target/classes/", "");
    private static final String JBSE_PATH = PROJECT_ROOT + "lib/jbse-0.10.0-SNAPSHOT-shaded.jar";
    private static final String OUTPUT_FILE_PATH = PROJECT_ROOT + "../out/output.txt";
    private static final String EXCEPTION_FLAG = "Leaf state, raised exception";
    private static final Pattern EXCEPTION_EXTRACTOR_PATTERN = Pattern.compile("Class\\[\\(\\d, (.*)\\)\\]: \\{");

    private final Properties properties;
    private final OutputFileReader outputFileReader;

    public SymbolicExceptionAnalyzer(Properties properties, OutputFileReader outputFileReader) {
        this.properties = properties;
        this.outputFileReader = outputFileReader;
    }

    private Run createRun(AnalysisProperties analysisProperties) {
        RunParameters runParameters = new RunParameters();
        runParameters.setJBSELibPath(JBSE_PATH);
        runParameters.setStateFormatMode(TEXT);
        runParameters.setStepShowMode(LEAVES);
        runParameters.setDecisionProcedureType(Z3);
        runParameters.setExternalDecisionProcedurePath(properties.getZ3Location());
        runParameters.setMethodSignature(
                analysisProperties.getClassName(),
                analysisProperties.getMethodDescriptor(),
                analysisProperties.getMethodName()
        );
        runParameters.setOutputFilePath(OUTPUT_FILE_PATH);
        runParameters.addUserClasspath(analysisProperties.getClasspath());
        return new Run(runParameters);
    }

    public List<SymbolicExecutionExceptionInformation> evaluateProgram(AnalysisProperties analysisProperties) throws IOException {
        createRun(analysisProperties).run();
        List<String> thrownExs = outputFileReader.readExceptionsFromFile(OUTPUT_FILE_PATH);
        return thrownExs.stream().map(e -> {
            int pkgSep = analysisProperties.getClassName().lastIndexOf('/');

            return new SymbolicExecutionExceptionInformation(e, new OriginMethod(
                    analysisProperties.getClassName().substring(0, pkgSep),
                    analysisProperties.getClassName().substring(pkgSep + 1),
                    analysisProperties.getMethodName(),
                    analysisProperties.getParameters().stream().map(Parameter::new).collect(Collectors.toList())
            ));
        }).collect(Collectors.toList());
    }

    @Component
    @ConfigurationProperties("javadoc-throws.cross-checker.jbse")
    public static class Properties {
        private String z3Location;

        public String getZ3Location() {
            return z3Location;
        }

        public void setZ3Location(String z3Location) {
            this.z3Location = z3Location;
        }
    }

}
