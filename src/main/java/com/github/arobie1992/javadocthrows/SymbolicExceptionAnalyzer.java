package com.github.arobie1992.javadocthrows;

import com.github.arobie1992.javadocthrows.exceptioninfo.DocExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.OriginMethod;
import com.github.arobie1992.javadocthrows.exceptioninfo.SymbolicExecutionExceptionInformation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SymbolicExceptionAnalyzer {

    public List<SymbolicExecutionExceptionInformation> evaluateProgram(Properties properties) {
        return Arrays.asList(
                new SymbolicExecutionExceptionInformation(
                        IllegalStateException.class,
                        new OriginMethod(
                                "com.github.arobie.test",
                                "TestClassA",
                                "testMethod",
                                Collections.emptyList()
                        )
                ),
                new SymbolicExecutionExceptionInformation(
                        NullPointerException.class,
                        new OriginMethod(
                                "com.github.arobie.test",
                                "TestClassA",
                                "testMethod",
                                Collections.emptyList()
                        )
                )
        );
    }

    public static class Properties {

    }

}
