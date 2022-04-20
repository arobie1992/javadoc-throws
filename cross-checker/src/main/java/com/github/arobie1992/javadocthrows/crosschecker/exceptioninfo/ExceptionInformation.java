package com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionInformation that = (ExceptionInformation) o;
        return Objects.equals(exception, that.exception) && Objects.equals(originMethod, that.originMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exception, originMethod);
    }
}
