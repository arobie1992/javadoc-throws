package com.github.arobie1992.javadocthrows.crosschecker.file;

import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.DocExceptionInformation;
import com.github.arobie1992.javadocthrows.crosschecker.exceptioninfo.OriginMethod;
import com.github.chhorz.javadoc.JavaDoc;
import com.github.chhorz.javadoc.JavaDocParser;
import com.github.chhorz.javadoc.JavaDocParserBuilder;
import com.github.chhorz.javadoc.tags.Tag;
import com.github.chhorz.javadoc.tags.ThrowsTag;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DocExceptionExtractor {

    private final JavadocFileReader javadocFileReader;

    public DocExceptionExtractor(JavadocFileReader javadocFileReader) {
        this.javadocFileReader = javadocFileReader;
    }

    public List<DocExceptionInformation> readExceptions(FileProperties properties) throws IOException {
        Set<DocExceptionInformation> documentedExceptions = new HashSet<>();
        List<RawFileJavadoc> rawDocs = javadocFileReader.readRaw(properties);
        rawDocs = rawDocs.stream().filter(r -> {
            OriginMethod m = r.getOriginMethod();
            String fullClass = m.getPackageName() + "." + m.getClassName();
            return fullClass.equals(properties.analyzedClass())
                   && r.getOriginMethod().getMethodName().equals(properties.analyzedMethodName())
                   && r.getOriginMethod().getParameterList().equals(properties.analyzedMethodParameters());
        }).collect(Collectors.toList());
        JavaDocParser p = JavaDocParserBuilder.withBasicTags().build();
        for(RawFileJavadoc raw : rawDocs) {
            JavaDoc doc = p.parse(raw.getJavadocString());
            for(Tag tag : doc.getTags(ThrowsTag.class)) {
                ThrowsTag tt = (ThrowsTag) tag;
                documentedExceptions.add(new DocExceptionInformation(tt.getClassName(), raw.getOriginMethod()));
            }
        }
        return new ArrayList<>(documentedExceptions);
    }

}
