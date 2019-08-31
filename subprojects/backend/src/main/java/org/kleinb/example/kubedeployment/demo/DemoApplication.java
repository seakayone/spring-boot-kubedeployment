package org.kleinb.example.kubedeployment.demo;

import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public VavrModule vavrJacksonModule() {
    return new VavrModule();
  }
}
