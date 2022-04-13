package com.github.arobie1992.javadocthrows.crosschecker.file;

public class FileProperties {

    private String analyzedClass;
    private String sourceRoot;
    private String simplifiedFile;

    public String analyzedClass() {
        return analyzedClass;
    }

    public FileProperties analyzedClass(String analyzedClass) {
        this.analyzedClass = analyzedClass;
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
