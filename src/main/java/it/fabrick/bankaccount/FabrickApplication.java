package it.fabrick.bankaccount;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Fabrick API", version = "4.0", description = "Endpoints that allows user's bank operations"))
public class FabrickApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(FabrickApplication.class);

	@Autowired
	RestProperties restProperties;

	public static void main(String[] args) {
		SpringApplication.run(FabrickApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("AccountId from local file......" + restProperties.getFabrick().getAccountId());

	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		interceptors.add(new RestTemplateHeaderInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

	@Bean(name = "jsonMapperBean")
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.registerModule(new JavaTimeModule());
	}
}
