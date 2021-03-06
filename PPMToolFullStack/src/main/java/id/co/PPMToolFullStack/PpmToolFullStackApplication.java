package id.co.PPMToolFullStack;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@SpringBootApplication
public class PpmToolFullStackApplication extends SpringBootServletInitializer {
//public class PpmToolFullStackApplication {
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	};
	
	
	public static void main(String[] args) {
		SpringApplication.run(PpmToolFullStackApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}

//	public static void main(String[] args) {
//		SpringApplicationBuilder springApplicationBuilder = (SpringApplicationBuilder) new SpringApplicationBuilder(
//				PpmToolFullStackApplication.class).sources(PpmToolFullStackApplication.class)
//						.properties(getProperties()).run(args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		// TODO Auto-generated method stub
//		return builder.sources(PpmToolFullStackApplication.class).properties(getProperties());
//	}
//
//	static Properties getProperties() {
//		Properties properties = new Properties();
//		properties.put("spring.config.location", "classspath:PPMTool");
//		return properties;
//	}

}
