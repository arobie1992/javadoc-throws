package com.github.arobie1992.javadocthrows.crosschecker.analysis;

public class DescriptorUtils {
    private DescriptorUtils() {}

    /*
    B             byte       signed byte
    C             char       Unicode character code point in the Basic
                               Multilingual Plane, encoded with UTF-16
    D             double     double-precision floating-point value
    F             float      single-precision floating-point value
    I             int        integer
    J             long       long integer
    L ClassName;  reference  an instance of class ClassName
    S             short      signed short
    Z             boolean    true or false
    [             reference  one array dimension
     */

    public static String toDescriptor(String type) {
        switch(type) {
            case "byte":
                return "B";
            case "char":
                return "C";
            case "double":
                return "D";
            case "float":
                return "F";
            case "int":
                return "I";
            case "long":
                return "J";
            case "short":
                return "S";
            case "boolean":
                return "Z";
            case "void":
                return "V";
            default:
                StringBuilder sb = new StringBuilder();
                if(type.endsWith("]")) {
                    for(int i = type.length() - 1; i >=0; i--) {
                        if(type.charAt(i) != ']') {
                            break;
                        }
                        sb.append('[');
                    }
                    sb.append(toDescriptor(type.substring(0, type.indexOf('['))));
                    return sb.toString();
                } else {
                    return "L" + type.replaceAll("\\.", "/");
                }
        }
    }
}
