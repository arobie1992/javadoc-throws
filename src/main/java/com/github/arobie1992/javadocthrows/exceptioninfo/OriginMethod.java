package com.github.arobie1992.javadocthrows.exceptioninfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OriginMethod {

    private final String packageName;
    private final String className;
    private final String methodName;
    private final List<Parameter> parameterList;

    public OriginMethod(String packageName, String className, String methodName, List<Parameter> parameterList) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.parameterList = new ArrayList<>(parameterList);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OriginMethod that = (OriginMethod) o;
        return packageName.equals(that.packageName)
                && className.equals(that.className)
                && methodName.equals(that.methodName)
                && parameterList.equals(that.parameterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, className, methodName, parameterList);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        for(Parameter p : parameterList) {
            sj.add(p.toString());
        }
        return packageName + "." + className + "#" + methodName + sj;
    }
}
