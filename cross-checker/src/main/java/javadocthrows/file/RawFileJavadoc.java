package javadocthrows.file;

import javadocthrows.exceptioninfo.OriginMethod;
import javadocthrows.exceptioninfo.Parameter;

import java.util.ArrayList;
import java.util.List;

public class RawFileJavadoc {

    private final String javadocString;
    private final OriginMethod originMethod;

    private RawFileJavadoc(Builder b) {
        this.javadocString = b.javadocSb.toString();
        this.originMethod = new OriginMethod(b.packageName, b.className, b.methodName, b.parameterList);
    }

    public String getJavadocString() {
        return javadocString;
    }

    public OriginMethod getOriginMethod() {
        return originMethod;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private final StringBuilder javadocSb = new StringBuilder();
        private String packageName;
        private String className;
        private String methodName;
        private final List<Parameter> parameterList = new ArrayList<>();

        private Builder() {}

        Builder appendDoc(CharSequence cs) {
            javadocSb.append(cs);
            return this;
        }

        Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        Builder className(String className) {
            this.className = className;
            return this;
        }

        Builder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        Builder addParam(Parameter parameter) {
            parameterList.add(parameter);
            return this;
        }

        RawFileJavadoc build() {
            return new RawFileJavadoc(this);
        }
    }
}
