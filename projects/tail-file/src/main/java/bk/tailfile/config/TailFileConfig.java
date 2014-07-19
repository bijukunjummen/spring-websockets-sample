package bk.tailfile.config;

import bk.tailfile.service.FileContentRecordingService;
import bk.tailfile.service.RandomQuoteGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.support.Pollers;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.file.tail.ApacheCommonsFileTailingMessageProducer;

import java.io.File;

//@Configuration
public class TailFileConfig {

	@Autowired
	private FileContentRecordingService fileContentRecordingService;

	public MessageProducerSupport fileContentProducer() {
		ApacheCommonsFileTailingMessageProducer tailFileProducer = new ApacheCommonsFileTailingMessageProducer();
		tailFileProducer.setFile(new File(new File(System.getProperty("java.io.tmpdir")), "quotes.txt"));
		return tailFileProducer;
	}

	@Bean
	public IntegrationFlow tailFilesFlow() {
		return IntegrationFlows.from(this.fileContentProducer())
				.handle("fileContentRecordingService", "sendLinesToTopic")
				.get();
	}
}
