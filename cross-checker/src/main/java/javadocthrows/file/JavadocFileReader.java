package javadocthrows.file;

import javadocthrows.exceptioninfo.Parameter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavadocFileReader {

    private JavadocSimplifier simplifier;

    public JavadocFileReader(JavadocSimplifier simplifier) {
        this.simplifier = simplifier;
    }

    public List<RawFileJavadoc> readRaw(FileProperties properties) throws IOException {
        simplifier.simplify(properties);
        List<RawFileJavadoc> rawDocs = new ArrayList<>();
        RawFileJavadoc.Builder curDocBuilder = null;
        try(BufferedReader r = new BufferedReader(new FileReader(new File(properties.simplifiedFile())))) {
            String line = r.readLine();
            while(line != null) {
                if(line.startsWith("PACKAGE: ")) {
                    curDocBuilder = RawFileJavadoc.builder();
                    curDocBuilder.packageName(line.replace("PACKAGE: ", ""));
                } else if(line.startsWith("CLASS: ")) {
                    curDocBuilder.className(line.replace("CLASS: ", ""));
                } else if(line.startsWith("METHOD: ")) {
                    addMethodInfo(curDocBuilder, line.replace("METHOD: ", ""));
                } else if(line.equals("DOC_COMMENT")) {
                    addDocComment(r, curDocBuilder);
                    rawDocs.add(curDocBuilder.build());
                    curDocBuilder = null;
                }
                line = r.readLine();
            }
        }
        return rawDocs;
    }

    private void addMethodInfo(RawFileJavadoc.Builder curDocBuilder, String line) {
        int openParenIdx = line.indexOf('(');
        curDocBuilder.methodName(line.substring(0, openParenIdx));
        String argsStr = line.substring(openParenIdx + 1, line.length() - 1);
        String[] args = argsStr.split(",");
        for(String a : args) {
            if(a != null && !a.equals("")) {
                curDocBuilder.addParam(new Parameter(a));
            }
        }
    }

    private void addDocComment(BufferedReader reader, RawFileJavadoc.Builder curDocBuilder) throws IOException {
        String line = reader.readLine();
        while(line != null && !line.equals("END_DOC_COMMENT")) {
            curDocBuilder.appendDoc(line + System.lineSeparator());
            line = reader.readLine();
        }
    }

}
