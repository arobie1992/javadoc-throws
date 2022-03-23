package com.github.arobie1992.javadocthrows.exceptioninfo;

public final class SymbolicExecutionExceptionInformation extends ExceptionInformation {
    public SymbolicExecutionExceptionInformation(Class<? extends RuntimeException> exception, OriginMethod originMethod) {
        super(exception, originMethod);
    }
}
