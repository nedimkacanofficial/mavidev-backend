package com.mavidev.project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Mavidev App", version = "1.0.0", description = "This is a mavidev app swagger documentation."),
    servers = {
        @Server(url = "https://mavidev-backend-production.up.railway.app", description = "Backend Server URL")
    }
)
public class MavidevApplication {

    public static void main(String[] args) {
        SpringApplication.run(MavidevApplication.class, args);
    }

}
