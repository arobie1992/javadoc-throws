package com.github.arobie1992.javadocthrows;

import com.github.arobie1992.javadocthrows.exceptioninfo.DocExceptionInformation;
import com.github.arobie1992.javadocthrows.exceptioninfo.SymbolicExecutionExceptionInformation;
import com.github.arobie1992.javadocthrows.writer.ResultsWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@SpringBootApplication
public class JavadocThrowsApplication {

	private final SymbolicExceptionAnalyzer symbolicExceptionAnalyzer;
	private final DocExceptionExtractor docExceptionExtractor;
	private final JavadocExceptionVerifier javadocExceptionVerifier;
	private final ResultsWriter resultsWriter;
	private final String outputFilePath;

	public JavadocThrowsApplication(
			SymbolicExceptionAnalyzer symbolicExceptionAnalyzer,
			DocExceptionExtractor docExceptionExtractor,
			JavadocExceptionVerifier javadocExceptionVerifier,
			ResultsWriter resultsWriter,
			@Value("${output.file-path}") String outputFilePath
	) {
		this.symbolicExceptionAnalyzer = symbolicExceptionAnalyzer;
		this.docExceptionExtractor = docExceptionExtractor;
		this.javadocExceptionVerifier = javadocExceptionVerifier;
		this.resultsWriter = resultsWriter;
		this.outputFilePath = outputFilePath;
	}

	public static void main(String[] args) {
		SpringApplication.run(JavadocThrowsApplication.class, args);
	}

	@PostConstruct
	public void run() {
		List<SymbolicExecutionExceptionInformation> symbolicExceptions =
				symbolicExceptionAnalyzer.evaluateProgram(new SymbolicExceptionAnalyzer.Properties());

		List<DocExceptionInformation> docExceptions = docExceptionExtractor.readExceptions(new DocExceptionExtractor.Properties());
		JavadocExceptionVerifier.DocDiff docDiff = javadocExceptionVerifier.compare(docExceptions, symbolicExceptions);

		try(Writer writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			resultsWriter.writeResults(docDiff, writer);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
