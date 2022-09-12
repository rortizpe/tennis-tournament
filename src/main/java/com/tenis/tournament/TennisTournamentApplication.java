package com.tenis.tournament;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API TENIS TOURNAMENT",
  version = "1.0",
  description = "Documentation API Tenis Tournament"))
public class TennisTournamentApplication {

  public static void main(String[] args) throws UnknownHostException {
    Environment env = SpringApplication.run(TennisTournamentApplication.class, args).getEnvironment();
    log.info("\n----------------------------------------------------------\n\t"
        .concat("Application '{}' is running! Access URLs:\n\t")
        .concat("Local: \t\thttp://localhost:{}\n\t")
        .concat("External: \thttp://{}:{}\n\t")
        .concat("DB: \t{}\n\t")
        .concat("Profile(s): \t{}\n----------------------------------------------------------"),
      env.getProperty("spring.application.name"),
      env.getProperty("server.port"),
      InetAddress.getLocalHost().getHostAddress(),
      env.getProperty("server.port"),
      env.getProperty("spring.data.mongodb.database"),
      env.getActiveProfiles());

    String configServerStatus = env.getProperty("configserver.status");
    log.info("\n----------------------------------------------------------\n\t"
        .concat("Config Server: \t{}\n----------------------------------------------------------"),
      configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
  }
}
