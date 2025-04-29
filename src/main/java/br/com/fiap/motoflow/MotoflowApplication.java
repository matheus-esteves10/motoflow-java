package br.com.fiap.motoflow;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Api do Motoflow", description = "Projeto de controle de patio de motos", version = "v1"))
public class MotoflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoflowApplication.class, args);
	}

}
