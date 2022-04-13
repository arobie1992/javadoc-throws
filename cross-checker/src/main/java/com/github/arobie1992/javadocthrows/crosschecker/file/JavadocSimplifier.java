package com.github.arobie1992.javadocthrows.crosschecker.file;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Component
public class JavadocSimplifier {

    private static final String JAVADOC_COMMAND_TEMPLATE = "javadoc " +
            "-doclet com.github.arobie1992.javadocthrows.doclet.SimpleDocAndMethodOutputDoclet " +
            "-docletpath doclet/target/classes " +
            "--out-file {outfile} " +
            "--analyzed-class {analyzedClass} {necessaryClasses}";

    public void simplify(FileProperties properties) throws IOException {
        deletePreviousRun(properties);
        Process p = Runtime.getRuntime().exec(parameterize(properties));
        int size = 0;
        byte[] buffer = new byte[1024];
        InputStream in = p.getInputStream();
        while ((size = in.read(buffer)) != -1) System.out.write(buffer, 0, size);
    }

    private void deletePreviousRun(FileProperties properties) throws IOException {
        File f = new File(properties.simplifiedFile());
        System.out.println(f.getAbsolutePath());
        if(!f.exists()) {
            return;
        }
        if(!f.delete()) {
            throw new IOException("Failed to delete previous run");
        }
    }

    private String parameterize(FileProperties properties) {
        String withClass = JAVADOC_COMMAND_TEMPLATE.replace("{analyzedClass}", properties.analyzedClass());
        String withOutFile = withClass.replace("{outfile}", properties.simplifiedFile());
        List<String> sources = getAllSourceFiles(new File(properties.sourceRoot()));
        StringJoiner sj = new StringJoiner(" ");
        sources.forEach(sj::add);
        String cmd = withOutFile.replace("{necessaryClasses}", sj.toString());
        return cmd;
    }

    private List<String> getAllSourceFiles(File root) {
        List<String> files = new ArrayList<>();
        for (File fileEntry : root.listFiles()) {
            if (fileEntry.isDirectory()) {
                files.addAll(getAllSourceFiles(fileEntry));
            } else {
                if(fileEntry.getName().endsWith(".java")) {
                    files.add(fileEntry.getAbsolutePath());
                }
            }
        }
        return files;
    }

}
