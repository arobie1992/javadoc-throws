package com.github.arobie1992.javadocthrows.crosschecker.file;

import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.Parameter;

import java.util.List;

public class FileProperties {

    private String analyzedClass;
    private String analyzedMethodName;
    private List<Parameter> analyzedMethodParameters;
    private String sourceRoot;
    private String simplifiedFile;

    public String analyzedClass() {
        return analyzedClass;
    }

    public FileProperties analyzedClass(String analyzedClass) {
        this.analyzedClass = analyzedClass;
        return this;
    }

    public String analyzedMethodName() {
        return analyzedMethodName;
    }

    public FileProperties analyzedMethodName(String analyzedMethodName) {
        this.analyzedMethodName = analyzedMethodName;
        return this;
    }

    public List<Parameter> analyzedMethodParameters() {
        return analyzedMethodParameters;
    }

    public FileProperties analyzedMethodParameters(List<Parameter> analyzedMethodParameters) {
        this.analyzedMethodParameters = analyzedMethodParameters;
        return this;
    }

    public String sourceRoot() {
        return sourceRoot;
    }

    public FileProperties sourceRoot(String sourceRoot) {
        this.sourceRoot = sourceRoot;
        return this;
    }

    public String simplifiedFile() {
        return simplifiedFile;
    }

    public FileProperties simplifiedFile(String outfile) {
        this.simplifiedFile = outfile;
        return this;
    }

}
