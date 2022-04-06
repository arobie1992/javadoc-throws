package javadocthrows;

import javadocthrows.exceptioninfo.DocExceptionInformation;
import javadocthrows.exceptioninfo.SymbolicExecutionExceptionInformation;
import javadocthrows.file.DocExceptionExtractor;
import javadocthrows.file.FileProperties;
import javadocthrows.writer.ResultsWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@SpringBootApplication
public class CrossCheckerApplication {

	private final SymbolicExceptionAnalyzer symbolicExceptionAnalyzer;
	private final DocExceptionExtractor docExceptionExtractor;
	private final JavadocExceptionVerifier javadocExceptionVerifier;
	private final ResultsWriter resultsWriter;
	private final String outputFilePath;
	private final String analyzedClass;
	private final String sourcesRoot;

	public CrossCheckerApplication(
			SymbolicExceptionAnalyzer symbolicExceptionAnalyzer,
			DocExceptionExtractor docExceptionExtractor,
			JavadocExceptionVerifier javadocExceptionVerifier,
			ResultsWriter resultsWriter,
			@Value("${output.file-path}") String outputFilePath,
			@Value("${target-class}") String analyzedClass,
			@Value("${sources-root}") String sourcesRoot
	) {
		this.symbolicExceptionAnalyzer = symbolicExceptionAnalyzer;
		this.docExceptionExtractor = docExceptionExtractor;
		this.javadocExceptionVerifier = javadocExceptionVerifier;
		this.resultsWriter = resultsWriter;
		this.outputFilePath = outputFilePath;
		this.analyzedClass = analyzedClass;
		this.sourcesRoot = sourcesRoot;
	}

	public static void main(String[] args) {
		SpringApplication.run(CrossCheckerApplication.class, args);
	}

	@PostConstruct
	public void run() {
		List<SymbolicExecutionExceptionInformation> symbolicExceptions =
				symbolicExceptionAnalyzer.evaluateProgram(new SymbolicExceptionAnalyzer.Properties());

		List<DocExceptionInformation> docExceptions = null;
		try {
			FileProperties p = new FileProperties()
					.analyzedClass(analyzedClass)
					.sourceRoot(sourcesRoot)
					.simplifiedFile("out/simplified.txt");
			docExceptions = docExceptionExtractor.readExceptions(p);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		JavadocExceptionVerifier.DocDiff docDiff = javadocExceptionVerifier.compare(docExceptions, symbolicExceptions);

		try(Writer writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			resultsWriter.writeResults(docDiff, writer);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
