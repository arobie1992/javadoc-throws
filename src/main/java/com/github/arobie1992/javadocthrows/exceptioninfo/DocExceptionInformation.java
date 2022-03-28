package com.github.arobie1992.javadocthrows.exceptioninfo;

public final class DocExceptionInformation extends ExceptionInformation {
    public DocExceptionInformation(Class<? extends RuntimeException> exception, OriginMethod originMethod) {
        super(exception, originMethod);
    }
}
