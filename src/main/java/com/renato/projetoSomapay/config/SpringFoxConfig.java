package com.renato.projetoSomapay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.renato.projetoSomapay.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("API de Folha de Pagamento")
	            .description("Uma API que faz operações de transferência bancária de uma Empresa a um Funcionário")
	            .version("1.0.0")
	            .license("Apache License Version 2.0")
	            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	            .contact(new Contact("Renato Guedes", "https://www.linkedin.com/in/renatoguedess/", "renato.guedes14@gmail.com"))
	            .build();
	}
}
