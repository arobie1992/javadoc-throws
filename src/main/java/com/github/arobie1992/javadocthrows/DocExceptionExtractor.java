package com.github.arobie1992.javadocthrows;

import com.github.arobie1992.javadocthrows.exceptioninfo.DocExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.OriginMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DocExceptionExtractor {

    public List<DocExceptionInformation> readExceptions(Properties properties) {
        return Arrays.asList(
                new DocExceptionInformation(
                        IllegalArgumentException.class,
                        new OriginMethod(
                                "com.github.arobie.test",
                                "TestClassA",
                                "testMethod",
                                Collections.emptyList()
                        )
                ),
                new DocExceptionInformation(
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
