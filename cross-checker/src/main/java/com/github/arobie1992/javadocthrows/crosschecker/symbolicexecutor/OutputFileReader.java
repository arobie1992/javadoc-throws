package com.github.arobie1992.javadocthrows.crosschecker.symbolicexecutor;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OutputFileReader {

    private static final Pattern UNCHECKED_EX_PATTERN = Pattern.compile("Leaf state, raised exception: .* -- Unchecked: (.*)");

    public List<String> readExceptionsFromFile(String filePath) throws IOException {
        List<String> exs = new ArrayList<>();
        try(BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            for(String line = r.readLine(); line != null; line = r.readLine()) {
                Matcher m = UNCHECKED_EX_PATTERN.matcher(line);
                if(m.matches()) {
                    String thrownEx = m.group(1);
                    exs.add(thrownEx.replaceAll("/", "."));
                }
            }
        }
        return exs;
    }

}
