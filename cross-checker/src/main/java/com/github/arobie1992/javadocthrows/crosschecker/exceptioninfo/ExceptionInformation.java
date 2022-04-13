package com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo;

abstract class ExceptionInformation {

    private final String exception;

    private final OriginMethod originMethod;

    ExceptionInformation(String exception, OriginMethod originMethod) {
        this.exception = exception;
        this.originMethod = originMethod;
    }

    public String exception() {
        return exception;
    }

    public OriginMethod originMethod() {
        return originMethod;
    }

}
