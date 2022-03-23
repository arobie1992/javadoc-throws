package com.github.arobie1992.javadocthrows.writer;

import com.github.arobie1992.javadocthrows.*;
import com.github.arobie1992.javadocthrows.exceptioninfo.OriginMethod;
import com.github.arobie1992.javadocthrows.exceptioninfo.Parameter;
import com.github.arobie1992.javadocthrows.exceptioninfo.UndeclaredExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.UnthrownExceptionInformation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Component
public class ResultsWriter {

    public void writeResults(JavadocExceptionVerifier.DocDiff docDiff, Writer writer) throws IOException {
        Map<OriginMethod, MethodExceptionInformation> methodInformation = new HashMap<>();

        docDiff.getUndeclardExceptions().forEach(e ->
                methodInformation.computeIfAbsent(e.originMethod(), MethodExceptionInformation::new).undeclaredExceptions.add(e));

        docDiff.getUnthrownExceptions().forEach(e ->
            methodInformation.computeIfAbsent(e.originMethod(), MethodExceptionInformation::new).unthrownExceptions.add(e));

        List<MethodExceptionInformation> ordered = new ArrayList<>(methodInformation.values());
        Collections.sort(ordered);

        for(MethodExceptionInformation mei : ordered) {
            writer.write("Method: " + mei.originMethod + System.lineSeparator());
            writer.write("Potentially throws the following exceptions but does not declare them:" + System.lineSeparator());
            for(UndeclaredExceptionInformation e : mei.undeclaredExceptions) {
                writer.write("\t" + e.exception().getCanonicalName() + System.lineSeparator());
            }

            writer.write("Declares the following exceptions but does not throw them:" + System.lineSeparator());

            for(UnthrownExceptionInformation e : mei.unthrownExceptions) {
                writer.write("\t" + e.exception().getCanonicalName() + System.lineSeparator());
            }
        }
    }

    private static class MethodExceptionInformation implements Comparable<MethodExceptionInformation> {
        private final OriginMethod originMethod;
        private final List<UndeclaredExceptionInformation> undeclaredExceptions = new ArrayList<>();
        private final List<UnthrownExceptionInformation> unthrownExceptions = new ArrayList<>();

        public MethodExceptionInformation(OriginMethod originMethod) {
            this.originMethod = originMethod;
        }

        @Override
        public int compareTo(MethodExceptionInformation o) {
            int pkgCmp = this.originMethod.getPackageName().compareTo(o.originMethod.getPackageName());
            if(pkgCmp != 0) {
                return pkgCmp;
            }

            int classCmp = this.originMethod.getClassName().compareTo(o.originMethod.getClassName());
            if(classCmp != 0) {
                return classCmp;
            }

            int methCmp = this.originMethod.getMethodName().compareTo(o.originMethod.getMethodName());
            if(methCmp != 0) {
                return methCmp;
            }

            Iterator<Parameter> itr1 = this.originMethod.getParameterList().iterator();
            Iterator<Parameter> itr2 = o.originMethod.getParameterList().iterator();
            while(itr1.hasNext() && itr2.hasNext()) {
                Parameter p1 = itr1.next();
                Parameter p2 = itr2.next();

                int nCmp = p1.getName().compareTo(p2.getName());
                if(nCmp != 0) {
                    return nCmp;
                }

                int tCmp = p1.getTypeName().compareTo(p2.getTypeName());
                if(tCmp != 0) {
                    return tCmp;
                }
            }

            if(itr1.hasNext()) {
                return -1;
            }

            if(itr2.hasNext()) {
                return 1;
            }

            return 0;
        }
    }

}
