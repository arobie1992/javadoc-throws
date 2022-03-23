package com.github.arobie1992.javadocthrows;

import com.github.arobie1992.javadocthrows.exceptioninfo.DocExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.SymbolicExecutionExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.UndeclaredExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.UnthrownExceptionInformation;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JavadocExceptionVerifier {

    public DocDiff compare(Collection<DocExceptionInformation> docExceptions, List<SymbolicExecutionExceptionInformation> symbolicExceptions) {
        Map<Class<? extends RuntimeException>, List<DocExceptionInformation>> distinctDocExceptions = docExceptions.stream()
                .collect(Collectors.groupingBy(DocExceptionInformation::exception));

        Map<Class<? extends RuntimeException>, List<SymbolicExecutionExceptionInformation>> distinctSymbolicExceptions = symbolicExceptions
                .stream().collect(Collectors.groupingBy(SymbolicExecutionExceptionInformation::exception));

        Map<Class<? extends RuntimeException>, List<SymbolicExecutionExceptionInformation>> undeclardExceptions =
                new HashMap<>(distinctSymbolicExceptions);
        distinctDocExceptions.keySet().forEach(undeclardExceptions::remove);

        Map<Class<? extends RuntimeException>, List<DocExceptionInformation>> unthrownExceptions = new HashMap<>(distinctDocExceptions);
        distinctSymbolicExceptions.keySet().forEach(unthrownExceptions::remove);

        return new DocDiff(
                undeclardExceptions.values().stream()
                        .flatMap(List::stream)
                        .map(e -> new UndeclaredExceptionInformation(e.exception(), e.originMethod()))
                        .collect(Collectors.toList()),
                unthrownExceptions.values().stream()
                        .flatMap(List::stream)
                        .map(e -> new UnthrownExceptionInformation(e.exception(), e.originMethod()))
                        .collect(Collectors.toList())
        );
    }

    public static final class DocDiff {
        private final List<UndeclaredExceptionInformation> undeclardExceptions;
        private final List<UnthrownExceptionInformation> unthrownExceptions;

        public DocDiff(
                List<UndeclaredExceptionInformation> undeclardExceptions,
                List<UnthrownExceptionInformation> unthrownExceptions
        ) {
            this.undeclardExceptions = undeclardExceptions;
            this.unthrownExceptions = unthrownExceptions;
        }

        public List<UndeclaredExceptionInformation> getUndeclardExceptions() {
            return undeclardExceptions;
        }

        public List<UnthrownExceptionInformation> getUnthrownExceptions() {
            return unthrownExceptions;
        }
    }

}
