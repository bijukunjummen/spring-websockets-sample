package bk.tailfile.config;

import bk.tailfile.service.RandomQuoteGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.support.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

//@Configuration
public class WriteFileConfig {
	@Autowired private RandomQuoteGenerator randomQuoteGenerator;

	@Bean
	public MessageSource<?> quoteSource() {
		MethodInvokingMessageSource source = new MethodInvokingMessageSource();
		source.setObject(this.randomQuoteGenerator);
		source.setMethodName("generateQuote");
		return source;
	}

	@Bean
	public IntegrationFlow writeQuotesFlow() {
		return IntegrationFlows.from(this.quoteSource(), c -> c.poller(Pollers.fixedRate(2000)))
				.enrichHeaders(s -> s.header("file_name", "quotes.txt"))
				.handle(fileWritingMessageHandler())
				.get();
	}

	@Bean
	public FileWritingMessageHandler fileWritingMessageHandler() {
		FileWritingMessageHandler fileWritingMessageHandler = new FileWritingMessageHandler(new File(System.getProperty("java.io.tmpdir")));
		fileWritingMessageHandler.setFileExistsMode(FileExistsMode.APPEND);
		fileWritingMessageHandler.setExpectReply(false);
		return fileWritingMessageHandler;
	}
}
