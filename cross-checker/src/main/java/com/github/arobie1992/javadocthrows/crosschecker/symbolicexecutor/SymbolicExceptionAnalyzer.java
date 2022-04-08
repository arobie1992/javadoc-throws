package com.github.arobie1992.javadocthrows.crosschecker.symbolicexecutor;

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
    private final Run run;
    private final OutputFileReader outputFileReader;

    public SymbolicExceptionAnalyzer(Properties properties, OutputFileReader outputFileReader) {
        this.properties = properties;
        RunParameters runParameters = new RunParameters();
        runParameters.setJBSELibPath(JBSE_PATH);
        runParameters.setStateFormatMode(TEXT);
        runParameters.setStepShowMode(LEAVES);
        runParameters.setDecisionProcedureType(Z3);
        runParameters.setExternalDecisionProcedurePath(properties.z3Location);
        runParameters.setMethodSignature(
                properties.targetMethod.containingClass,
                properties.targetMethod.descriptor,
                properties.targetMethod.name
        );
        runParameters.setOutputFilePath(OUTPUT_FILE_PATH);
        runParameters.addUserClasspath(properties.userClasspath);
        run = new Run(runParameters);
        this.outputFileReader = outputFileReader;
    }

    public List<SymbolicExecutionExceptionInformation> evaluateProgram() throws IOException {
        run.run();
        List<String> thrownExs = outputFileReader.readExceptionsFromFile(OUTPUT_FILE_PATH);
        return thrownExs.stream().map(e -> {
            String containingClass = properties.targetMethod.containingClass.replaceAll("/", ".");
            int pkgSep = containingClass.lastIndexOf('.');

            return new SymbolicExecutionExceptionInformation(e, new OriginMethod(
                    containingClass.substring(0, pkgSep),
                    containingClass.substring(pkgSep + 1),
                    properties.targetMethod.name,
                    getParamsList()
            ));
        }).collect(Collectors.toList());
    }

    private List<Parameter> getParamsList() {
        String descriptor = properties.targetMethod.descriptor;
        int end = descriptor.indexOf(')');
        String argsStr = descriptor.substring(1, end);
        String[] args = argsStr.split(",");
        //TODO add translation from descriptor type to Javadoc style name
        return Arrays.stream(args).filter(a -> a != null && !a.isEmpty()).map(a -> new Parameter(a)).collect(Collectors.toList());
    }

    @Component
    @ConfigurationProperties("static-analyzer")
    public static class Properties {
        private String userClasspath;
        private String z3Location;
        private TargetMethod targetMethod;

        public static class TargetMethod {
            private String containingClass;
            private String descriptor;
            private String name;

            public String getContainingClass() {
                return containingClass;
            }

            public void setContainingClass(String containingClass) {
                this.containingClass = containingClass;
            }

            public String getDescriptor() {
                return descriptor;
            }

            public void setDescriptor(String descriptor) {
                this.descriptor = descriptor;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public String getUserClasspath() {
            return userClasspath;
        }

        public void setUserClasspath(String userClasspath) {
            this.userClasspath = userClasspath;
        }

        public String getZ3Location() {
            return z3Location;
        }

        public void setZ3Location(String z3Location) {
            this.z3Location = z3Location;
        }

        public TargetMethod getTargetMethod() {
            return targetMethod;
        }

        public void setTargetMethod(TargetMethod targetMethod) {
            this.targetMethod = targetMethod;
        }
    }

}
