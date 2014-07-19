package bk.tailfile;

import bk.tailfile.config.WriteFileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


@Configuration
@EnableAutoConfiguration
@ComponentScan
//@Import(WriteFileConfig.class)
@ImportResource( {"classpath:/META-INF/spring/writetofile.xml", "classpath:/META-INF/spring/tailfile.xml"})
public class SampleWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleWebApplication.class, args);
	}
}
