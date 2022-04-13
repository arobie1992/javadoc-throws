package com.github.arobie1992.javadocthrows.doclet;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTreeScanner;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementScanner9;
import javax.lang.model.util.Elements;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SimpleDocAndMethodOutputDoclet implements Doclet {

    private DocTrees treeUtils;
    private String analyzedType;
    private String outfile;
    private Elements elementUtils;

    abstract class Option implements Doclet.Option {
        private final String name;
        private final boolean hasArg;
        private final String description;
        private final String parameters;

        Option(String name, boolean hasArg,
               String description, String parameters) {
            this.name = name;
            this.hasArg = hasArg;
            this.description = description;
            this.parameters = parameters;
        }

        @Override
        public int getArgumentCount() {
            return hasArg ? 1 : 0;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return List.of(name);
        }

        @Override
        public String getParameters() {
            return hasArg ? parameters : "";
        }
    }

    @Override
    public void init(Locale locale, Reporter reporter) {}

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    private final Set<? extends Option> options = Set.of(
            new Option(
                    "--analyzed-class",
                    true,
                    "Class to be anaylzed",
                    null
            ) {
                @Override
                public boolean process(String option, List<String> arguments) {
                    if(arguments.size() != 1) {
                        throw new IllegalArgumentException("Can only analyze one class at a time");
                    }
                    analyzedType = arguments.get(0);
                    return true;
                }
            },
            new Option(
                    "--out-file",
                    true,
                    "File to write results to",
                    null
            ) {
                @Override
                public boolean process(String option, List<String> arguments) {
                    if(arguments.size() != 1) {
                        throw new IllegalArgumentException("Need out file");
                    }
                    outfile = arguments.get(0);
                    return true;
                }
            }

    );

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return options;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        treeUtils = environment.getDocTrees();
        elementUtils = environment.getElementUtils();
        if(outfile == null || analyzedType == null) {
            throw new IllegalArgumentException("missing required params");
        }
        PrintStream reportStream;
        try {
            FileOutputStream f = new FileOutputStream(outfile, true);
            reportStream = new PrintStream(f);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
        ShowElements se = new ShowElements(reportStream);
        se.show(environment.getIncludedElements());
        return true;
    }

    /**
     * A scanner to display the structure of a series of elements
     * and their documentation comments.
     */
    class ShowElements extends ElementScanner9<Void, Integer> {
        final PrintStream out;

        ShowElements(PrintStream out) {
            this.out = out;
        }

        void show(Set<? extends Element> elements) {
            scan(elements, 0);
        }

        @Override
        public Void scan(Element e, Integer depth) {
            DocCommentTree dcTree = treeUtils.getDocCommentTree(e);
            if(analyzedType.equals(e.getEnclosingElement().toString()) && e.getKind() == ElementKind.METHOD) {
                if (dcTree != null) {
                    String pkg = elementUtils.getPackageOf(e).toString();
                    out.println("PACKAGE: " + pkg);
                    String type = e.getEnclosingElement().toString().replace(pkg + ".", "");
                    out.println("CLASS: " + type);
                    out.println("METHOD: " + e);
                    new ShowDocTrees(out).scan(dcTree, depth + 1);
                }
            }
            return super.scan(e, depth + 1);
        }
    }

    /**
     * A scanner to display the structure of a documentation comment.
     */
    class ShowDocTrees extends DocTreeScanner<Void, Integer> {
        final PrintStream out;

        ShowDocTrees(PrintStream out) {
            this.out = out;
        }

        @Override
        public Void scan(DocTree t, Integer depth) {
            if(t.getKind() == DocTree.Kind.DOC_COMMENT) {
                out.println("DOC_COMMENT");
                out.println(t.toString());
                out.println("END_DOC_COMMENT");
            }
            return super.scan(t, depth + 1);
        }
    }
}
