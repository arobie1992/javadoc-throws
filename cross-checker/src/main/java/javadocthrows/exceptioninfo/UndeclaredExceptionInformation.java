package javadocthrows.exceptioninfo;

public final class UndeclaredExceptionInformation extends ExceptionInformation {
    public UndeclaredExceptionInformation(Class<? extends RuntimeException> exception, OriginMethod originMethod) {
        super(exception, originMethod);
    }
}
