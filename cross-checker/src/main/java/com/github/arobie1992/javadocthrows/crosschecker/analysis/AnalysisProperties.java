package com.github.arobie1992.javadocthrows.crosschecker.analysis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("javadoc-throws.cross-checker.analysis")
public class AnalysisProperties {
    private String sourceRoot;
    private String classpath;
    private String className;
    private String methodName;
    private List<String> parameters;
    private String returnType;

    public String getSourceRoot() {
        return sourceRoot;
    }

    public void setSourceRoot(String sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodDescriptor() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        parameters.forEach(p -> sb.append(DescriptorUtils.toDescriptor(p)));
        sb.append(')');
        sb.append(DescriptorUtils.toDescriptor(returnType));
        return sb.toString();
    }
}
