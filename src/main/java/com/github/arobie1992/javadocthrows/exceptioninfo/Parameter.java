package com.github.arobie1992.javadocthrows.exceptioninfo;

import java.util.Objects;

public class Parameter {

    private final String typeName;
    private final String name;

    public Parameter(String typeName, String parameterName) {
        this.typeName = typeName;
        this.name = parameterName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return typeName.equals(parameter.typeName) && name.equals(parameter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, name);
    }

    @Override
    public String toString() {
        return "<" + typeName + " " + name + ">";
    }
}
