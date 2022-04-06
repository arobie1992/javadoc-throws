package javadocthrows.exceptioninfo;

abstract class ExceptionInformation {

    private final Class<? extends RuntimeException> exception;

    private final OriginMethod originMethod;

    ExceptionInformation(Class<? extends RuntimeException> exception, OriginMethod originMethod) {
        this.exception = exception;
        this.originMethod = originMethod;
    }

    public Class<? extends RuntimeException> exception() {
        return exception;
    }

    public OriginMethod originMethod() {
        return originMethod;
    }

}
