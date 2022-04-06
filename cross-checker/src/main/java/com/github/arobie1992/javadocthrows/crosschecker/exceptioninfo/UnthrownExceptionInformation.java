package com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo;

public final class UnthrownExceptionInformation extends ExceptionInformation {
    public UnthrownExceptionInformation(Class<? extends RuntimeException> exception, OriginMethod originMethod) {
        super(exception, originMethod);
    }
}
