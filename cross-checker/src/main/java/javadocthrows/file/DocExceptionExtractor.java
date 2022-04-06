package javadocthrows.file;

import javadocthrows.exceptioninfo.DocExceptionInformation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocExceptionExtractor {

    private final JavadocFileReader javadocFileReader;

    public DocExceptionExtractor(JavadocFileReader javadocFileReader) {
        this.javadocFileReader = javadocFileReader;
    }

    public List<DocExceptionInformation> readExceptions(FileProperties properties) throws IOException {
        List<RawFileJavadoc> rawDocs = javadocFileReader.readRaw(properties);
        return new ArrayList<>();
    }

}
